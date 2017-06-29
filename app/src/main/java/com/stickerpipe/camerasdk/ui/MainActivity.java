package com.stickerpipe.camerasdk.ui;

import android.Manifest;
import android.animation.Animator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.FileProvider;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.face.Landmark;
import com.google.gson.reflect.TypeToken;
import com.stickerpipe.camerasdk.Geometry;
import com.stickerpipe.camerasdk.Logger;
import com.stickerpipe.camerasdk.R;
import com.stickerpipe.camerasdk.StorageManager;
import com.stickerpipe.camerasdk.Utils;
import com.stickerpipe.camerasdk.network.NetworkManager;
import com.stickerpipe.camerasdk.network.StoriesResponse;
import com.stickerpipe.camerasdk.provider.stories.StoriesBean;
import com.stickerpipe.camerasdk.provider.stories.StoriesColumns;
import com.stickerpipe.camerasdk.provider.stories.StoriesCursor;
import com.stickerpipe.camerasdk.provider.storystamps.StoryStampsBean;
import com.stickerpipe.camerasdk.provider.storystamps.StoryStampsCursor;
import com.stickerpipe.camerasdk.provider.storystamps.StoryStampsSelection;
import com.stickerpipe.camerasdk.ui.fragment.StampsFragment;
import com.stickerpipe.camerasdk.ui.fragment.StampsListFragment;
import com.stickerpipe.camerasdk.ui.gesture.OnSwipeTouchListener;
import com.stickerpipe.camerasdk.ui.view.StampImageView;
import com.stickerpipe.camerasdk.vision.FaceView;
import com.stickerpipe.camerasdk.vision.SafeFaceDetector;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback, Camera.ShutterCallback, Camera.PictureCallback, LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int SELECT_PHOTO_CODE = 1021;
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private File photoFile;
    private ImageView photoImageView;
    private static final String STAMPS_FRAGMENT_TAG = "stamps_fragment_tag";
    private View stampsFrame;
    private LinkedList<StampImageView> stamps = new LinkedList<>();
    private RelativeLayout stampsContainer;
    private int stampPadding;
    private ImageView stampsButton;
    private Bitmap photoBitmap;
    private File photoForShareFile;
    private Camera mCamera;
    private SurfaceView mSurfaceView;
    private boolean isInEditMode;
    private int currentCameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
    private View editPhotoButtonsContainer;
    private View takePhotoButtonsContainer;
    private boolean useHorizontalFlip;
    private View shareButtonContainer;
    private boolean isPermissionsGranted;
    private PorterDuffColorFilter selectedItemFilterColor = new PorterDuffColorFilter(0xffcdcdcd, PorterDuff.Mode.MULTIPLY);
    private View complexMorphContainer;
    private FaceView overlay;
    private Face currentFace;
    private List<StoriesBean> stories = new ArrayList<>();
    private float density;
    private View storiesContainer;
    private StoriesAdapter storiesAdapter;
    private int storiesScrollAllPixels;
    private int itemWidth;
    private int currentStory = -1;
    private RecyclerView rv;
    private View closeButton;
    private View progress;
    private View takePhotoButton;
    private View backToStoriesButton;
    private boolean checkIsNeedSwitchToStoryEditMode = false;
    private float buttonsTranslation;
    private int stampSize;
    private static final int LOADER_ID = Utils.atomicInteger.incrementAndGet();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        hideStatusBar();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(android.R.id.content).setBackgroundColor(Color.BLACK);
        initViews();
        if (checkPermissions()) {
            isPermissionsGranted = true;
            init();
        }
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
//        try {
//            showImageFromInputStream(getAssets().open("1.png"));
//        } catch (IOException ignored) {
//            // =)
//        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                this,
                StoriesColumns.CONTENT_URI,
                new String[]{StoriesColumns.STORY_ID, StoriesColumns.ICON_LINK, StoriesColumns._ID},
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        StoriesCursor storiesCursor = new StoriesCursor(cursor);
        stories.clear();
        stories.add(new StoriesBean() {
            @Nullable
            @Override
            public Integer getStoryId() {
                return -1;
            }
        }); // first empty
        if (storiesCursor.moveToFirst()) {
            do {
                StoriesBean storyBean = new StoriesBean();
                storyBean.setStoryId(storiesCursor.getStoryId());
                storyBean.setIconLink(storiesCursor.getIconLink());
                stories.add(storyBean);
            } while (storiesCursor.moveToNext());
        }
        buildStoriesPager();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void hideStatusBar() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private boolean checkPermissions() {
        List<String> listPermissionsNeeded = new ArrayList<>();
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission);
            }
        }
        if (listPermissionsNeeded.isEmpty()) {
            return true;
        } else {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            init();
            initSurface();
            startCamera();
        } else {
            blockButtons();
            showMessage(R.string.dont_have_permissions);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isPermissionsGranted) {
            initSurface();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        tryStopCamera();
        mSurfaceView.setVisibility(View.GONE);
    }

    private void initSurface() {
        openCamera();
        mSurfaceView.getHolder().addCallback(this);
        mSurfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mSurfaceView.setVisibility(View.VISIBLE);
    }

    private void openCamera() {
        try {
            mCamera = Camera.open(currentCameraId);
            mCamera.setPreviewDisplay(mSurfaceView.getHolder());
        } catch (IOException e) {
            showMessage(R.string.failed_to_open_camera);
        } catch (RuntimeException ex) {
            // it means that we don't has permissions yes
        }
    }

    private void tryStopCamera() {
        if (mCamera != null) {
            try {
                mCamera.stopPreview();
                mCamera.release();
            } catch (RuntimeException e) {
                // camera already released
            }
        }
    }

    private void initViews() {
        stampsButton = (ImageView) findViewById(R.id.stamps_button);
        stampsButton.setOnTouchListener(imageSelectorTouchListener);
        stampsFrame = findViewById(R.id.stamps_list_frame);
        stampsContainer = (RelativeLayout) findViewById(R.id.stamps_container);
        photoImageView = (ImageView) findViewById(R.id.photo_image);
        ImageView switchCameraButton = (ImageView) findViewById(R.id.change_camera_button);
        switchCameraButton.setOnTouchListener(imageSelectorTouchListener);
        editPhotoButtonsContainer = findViewById(R.id.edit_photo_buttons_container);
        takePhotoButtonsContainer = findViewById(R.id.take_photo_buttons_container);
        progress = findViewById(R.id.progress);
        complexMorphContainer = findViewById(R.id.complex_morph_container);
        closeButton = findViewById(R.id.close_edit_button);
        closeButton.setOnTouchListener(imageSelectorTouchListener);
        closeButton.setOnClickListener(v -> {
            if (isStampsListOpen()) {
                toggleStampsFrame();
            } else {
                exitEditMode(false);
            }
        });
        mSurfaceView = (SurfaceView) findViewById(R.id.surface_view);
        mSurfaceView.setOnClickListener(v -> {
            if (mCamera != null) {
                mCamera.autoFocus(autoFocusCallback);
            }
        });
        takePhotoButton = findViewById(R.id.take_photo_button);
        takePhotoButton.setOnTouchListener(imageSelectorTouchListener);
        takePhotoButton.setOnClickListener(v -> {
            if (mCamera != null) {
                takePhoto();
            }
        });
        StampsFragment stampsFragment = (StampsFragment) getSupportFragmentManager().findFragmentByTag(STAMPS_FRAGMENT_TAG);
        if (stampsFragment == null) {
            stampsFragment = new StampsFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.stamps_list_frame, stampsFragment, STAMPS_FRAGMENT_TAG).commit();
        }
        stampsFragment.setStampSelectedLister(stampSelectedListener);
        View shareButton = findViewById(R.id.share_button);
        shareButtonContainer = findViewById(R.id.share_button_container);
        shareButton.setOnTouchListener(imageSelectorTouchListener);
        shareButton.setOnClickListener(v -> {
            if (!isStampActive)
                share();
        });
        View downloadButton = findViewById(R.id.download_button);
        downloadButton.setOnTouchListener(imageSelectorTouchListener);
        downloadButton.setOnClickListener(v -> downloadPhotoToLocalGallery());
        View loadPhotoButton = findViewById(R.id.load_photo_button);
        loadPhotoButton.setOnTouchListener(imageSelectorTouchListener);
        loadPhotoButton.setOnClickListener(v -> {
            Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, SELECT_PHOTO_CODE);
        });
        stampsButton.setOnClickListener(v -> toggleStampsFrame());
        photoImageView.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeBottom() {
                if (stamps.size() > 0) {
                    stamps.getLast().flipImageVertical();
                }
            }

            @Override
            public void onSwipeTop() {
                if (stamps.size() > 0) {
                    stamps.getLast().flipImageVertical();
                }
            }

            @Override
            public void onSwipeLeft() {
                if (stamps.size() > 0) {
                    stamps.getLast().flipImageHorizontal();
                }
            }

            @Override
            public void onSwipeRight() {
                if (stamps.size() > 0) {
                    stamps.getLast().flipImageHorizontal();
                }
            }
        });
        switchCameraButton.setOnClickListener(v -> {
            if (mCamera != null) {
                tryStopCamera();
                //swap the id of the camera to be used
                if (currentCameraId == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    currentCameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
                } else {
                    currentCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
                }
                openCamera();
                startCamera();
            }
        });
        findViewById(R.id.touch_interceptor_view).setOnTouchListener((v, event) -> {
            if (!isStampActive && event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                // calculate view real X and Y after scale
                // reset all previous force down flags
                for (int counter = stamps.size() - 1; counter >= 0; counter--) {
                    stamps.get(counter).setUseForceDown(false);
                }
                // check is user touch any non transparent pixel
                for (int counter = stamps.size() - 1; counter >= 0; counter--) {
                    StampImageView stampView = stamps.get(counter);
                    if (Utils.isPointInsideView(event.getX(), event.getY(), stampView)) {
                        int location[] = new int[2];
                        stampView.getLocationOnScreen(location);
                        int viewX = location[0];
                        int viewY = location[1];
                        if (stampView.isCanBeDown((event.getX() - viewX) / (stampView.getScaleX()), (event.getY() - viewY) / (stampView.getScaleY()))) {
                            stampView.setUseForceDown(true);
                            return false;
                        }
                    }
                }
                // check touch by view bounds
                for (int counter = stamps.size() - 1; counter >= 0; counter--) {
                    StampImageView stampView = stamps.get(counter);
                    if (Utils.isPointInsideView(event.getX(), event.getY(), stampView)) {
                        stampView.setUseForceDown(true);
                        return false;
                    }
                }
            }
            return false;
        });
        overlay = (FaceView) findViewById(R.id.face_view);
        storiesContainer = findViewById(R.id.stories_list_container);

        backToStoriesButton = findViewById(R.id.back_to_stories);
        backToStoriesButton.setOnTouchListener(imageSelectorTouchListener);
        backToStoriesButton.setOnClickListener(v -> setStoriesActive(true));

        // set buttons start buttonsTranslation
        buttonsTranslation = Utils.dp(160, this);
        shareButtonContainer.setTranslationX(buttonsTranslation);
        stampsButton.setTranslationX(buttonsTranslation);
        backToStoriesButton.setTranslationX(-buttonsTranslation);
        findViewById(R.id.reload).setOnClickListener(v -> reload());
    }

    private void reload() {
        NetworkManager.getInstance().requestStoriesUpdate();
    }

    private void downloadPhotoToLocalGallery() {
        try {
            Toast toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
            TextView toastTV = (TextView) ((ViewGroup) toast.getView()).getChildAt(0);
            toastTV.setTextSize(16);
            if (createFinalImage()) {
                toast.setText(R.string.photo_stored);
            } else {
                toast.setText(R.string.cant_store_photo);
            }
            toast.show();
        } catch (Exception e) {
            Logger.e(TAG, e);
            Toast.makeText(this, R.string.photo_stored, Toast.LENGTH_SHORT).show();
        }
    }

    private void applyStory(int storyId) {
        clearStamps();
        currentStory = storyId;
        if (currentFace == null) {
            Toast.makeText(this, R.string.face_not_detected, Toast.LENGTH_SHORT).show();
        } else {
            StoryStampsCursor stampsCursor = new StoryStampsSelection().storyId(storyId).query(getContentResolver());
            while (stampsCursor.moveToNext()) {
                StoryStampsBean stampBean = new StoryStampsBean.Builder()
                        .storyId(stampsCursor.getStoryId())
                        .stampId(stampsCursor.getStampId())
                        .link(stampsCursor.getLink())
                        .type(stampsCursor.getType())
                        .positionType(stampsCursor.getPositionType())
                        .points(stampsCursor.getPoints())
                        .scale(stampsCursor.getScale())
                        .rotation(stampsCursor.getRotation())
                        .offset(stampsCursor.getOffset())
                        .stampOrder(stampsCursor.getStampOrder())
                        .build();
                StampImageView stampView = createStampView(String.valueOf(stampBean.getStampId()), null, stampBean.getLink());
                if (positionStamp(stampView, stampBean)) {
                    addStamp(stampView);
                }
            }
            stampsCursor.close();
            animateStampsAppearing();
        }
    }

    private void animateStampsAppearing() {
        stampsContainer.setAlpha(0.1f);
        stampsContainer.setScaleX(0.2f);
        stampsContainer.setScaleY(0.2f);
        stampsContainer.animate().alpha(1).scaleX(1).scaleY(1).setDuration(200).start();
    }

    private void buildStoriesPager() {
        if (storiesAdapter == null) {
            rv = (RecyclerView) findViewById(R.id.stories_recycler_view);
            rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        calculatePositionAndScroll(recyclerView);
                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    storiesScrollAllPixels += dx;
                }
            });
            storiesAdapter = new StoriesAdapter();
            LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            rv.setLayoutManager(lm);
            OverScrollDecoratorHelper.setUpOverScroll(rv, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL);
            rv.setAdapter(storiesAdapter);
        }
        storiesAdapter.notifyDataSetChanged();
    }


    private void calculatePositionAndScroll(RecyclerView recyclerView) {
        int expectedPosition = Math.round((storiesScrollAllPixels + itemWidth / 2) / itemWidth);
        // Special cases for the padding items
        if (expectedPosition == -1) {
            expectedPosition = 0;
        } else if (expectedPosition >= recyclerView.getAdapter().getItemCount() - 2) {
            expectedPosition--;
        }
        scrollListToPosition(recyclerView, expectedPosition, true);
    }

    private void scrollListToPosition(RecyclerView recyclerView, int position, boolean smooth) {
        float targetScrollPos = position * itemWidth;
        float missingPx = targetScrollPos - storiesScrollAllPixels;
        if (missingPx != 0) {
            if (smooth) {
                recyclerView.smoothScrollBy((int) missingPx, 0);
            } else {
                recyclerView.scrollBy((int) missingPx, 0);
            }
        } else {
            int newStory = stories.get(position).getStoryId();
            if (currentStory != newStory) {
                applyStory(newStory);
            }
            if (checkIsNeedSwitchToStoryEditMode) {
                setStoriesActive(false);
            }
        }
        checkIsNeedSwitchToStoryEditMode = false;
    }


    private class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.ViewHolder> {
        private static final int VIEW_TYPE_PADDING = 1;
        private static final int VIEW_TYPE_ITEM = 2;


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == VIEW_TYPE_ITEM) {
                View tabView = LayoutInflater.from(parent.getContext()).inflate(R.layout.story_tab, null);
                return new ViewHolder(tabView);
            } else {
                View v = new View(parent.getContext());
                int itemSize = Utils.dp(96, parent.getContext());
                int screenSize = Utils.getScreenWidthInPx(parent.getContext());
                v.setLayoutParams(new ViewGroup.LayoutParams(screenSize / 2 - itemSize / 2, ViewGroup.LayoutParams.MATCH_PARENT));
                return new ViewHolder(v);
            }
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (getItemViewType(position) == VIEW_TYPE_ITEM) {
                int realPosition = position - 1;
                StoriesBean story = stories.get(realPosition);
                Glide.with(MainActivity.this)
                        .load(story.getIconLink())
                        .into(holder.iv);
                holder.iv.setOnClickListener(v -> {
                    checkIsNeedSwitchToStoryEditMode = true;
                    scrollListToPosition(rv, position - 1, true);
                });
            }
        }

        @Override
        public int getItemCount() {
            return stories.size() + 2;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0 || position == getItemCount() - 1) {
                return VIEW_TYPE_PADDING;
            }
            return VIEW_TYPE_ITEM;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView iv;

            ViewHolder(View v) {
                super(v);
                iv = (ImageView) v.findViewById(R.id.image);
            }
        }
    }

    private void exitEditMode(boolean forceExit) {
        overlay.setContent(null);
        if (forceExit || stamps.isEmpty()) {
            isInEditMode = false;
            if (isStampsListOpen()) {
                toggleStampsFrame();
            }
            clearStamps();
            setEditPhotoButtonsBlockVisible(false);
            setTakePhotoButtonsBlockVisible(true);
            closeButton.setVisibility(View.GONE);
            currentStory = -1;
            storiesContainer.setVisibility(View.GONE);
        } else {
            DialogManager.showExitEditModeDialog(this, (dialog, which) -> exitEditMode(true));
        }
    }

    private boolean isStampsListOpen() {
        return stampsFrame.getVisibility() == View.VISIBLE;
    }

    private void takePhoto() {
        try {
            mCamera.takePicture(this, null, this);
        } catch (Exception ignored) {
        }
    }

    private void init() {
        stampPadding = Utils.dp(32, this);
        stampSize = 160 * getDensity() + stampPadding * 2;
        String imagesFolder = StorageManager.getInstance().getImagesFolder();
        photoFile = new File(imagesFolder + "original.jpg");
        photoForShareFile = new File(imagesFolder + "final.jpg");
        try {
            File imagesFolderFile = new File(imagesFolder);
            if (!imagesFolderFile.exists()) {
                if (!imagesFolderFile.mkdirs()) {
                    onInitFailed();
                    return;
                }
            }
            if (!photoFile.exists()) {
                if (!photoFile.createNewFile()) {
                    onInitFailed();
                    return;
                }
            }
            if (!photoForShareFile.exists()) {
                if (!photoForShareFile.createNewFile()) {
                    onInitFailed();
                    return;
                }
            }
        } catch (IOException e) {
            onInitFailed();
        }
        itemWidth = Utils.dp(96, this);
        storiesScrollAllPixels = 0;
    }

    private void onInitFailed() {
        showMessage(R.string.failed_to_create_image);
        blockButtons();
    }

    private void blockButtons() {
        setTakePhotoButtonsBlockVisible(false);
        setEditPhotoButtonsBlockVisible(false);
    }

    private void setTakePhotoButtonsBlockVisible(boolean isVisible) {
        takePhotoButtonsContainer.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    private void setEditPhotoButtonsBlockVisible(boolean isVisible) {
        editPhotoButtonsContainer.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        photoImageView.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    private void share() {
        createFinalImage();
        startShareDialog();
        NetworkManager.getInstance().sendStoryUseEvent(currentStory);
    }

    private boolean createFinalImage() {
        try {
            complexMorphContainer.setDrawingCacheEnabled(true);
            complexMorphContainer.buildDrawingCache();
            Bitmap bitmap = Bitmap.createBitmap(complexMorphContainer.getDrawingCache());
            complexMorphContainer.getDrawingCache().recycle();
            complexMorphContainer.setDrawingCacheEnabled(false);
            Utils.compressBitmapToFile(bitmap, photoForShareFile, 70);
            bitmap.recycle();
            return storePhoto();
        } catch (IOException e) {
            showMessage(R.string.failed_to_create_image);
        }
        return false;
    }

    private void startShareDialog() {
        final Intent sharingIntent = new Intent();
        sharingIntent.setAction(Intent.ACTION_SEND);
        sharingIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Uri fileUri = FileProvider.getUriForFile(this, getPackageName() + ".provider", photoForShareFile);
        sharingIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
        sharingIntent.setType("image/png");
        DialogManager.showShareDialog(sharingIntent, this);
    }

    public boolean storePhoto() {
        try {
            String key = String.valueOf(System.currentTimeMillis()) + ".jpg";
            File finalImage = new File(StorageManager.getInstance().getImagesFolder() + StorageManager.KEY_IMAGE_FINAL + key);
            if (finalImage.createNewFile() && Utils.copyFile(photoForShareFile, finalImage)) {
                Utils.addImageToGallery(finalImage.getAbsolutePath(), this);
                return true;
            }
        } catch (IOException e) {
            Logger.e(TAG, e);
        }
        return false;
    }

    private StampsListFragment.StampSelectedListener stampSelectedListener = (stampId, link) -> {
        NetworkManager.getInstance().sendStampUseEvent(stampId);
        addStamp(stampId, link);
    };

    private void addStamp(int stampId, String link) {
        addStamp(createStampView(String.valueOf(stampId), null, link));
    }

    private StampImageView addStamp(StampImageView stampView) {
        stamps.add(stampView);
        stampsContainer.addView(stampView);
        if (isStampsListOpen()) {
            toggleStampsFrame();
        }
        return stampView;
    }

    private StampImageView createStampView(String contentId, Bitmap bitmap, String url) {
        StampImageView stampView = new StampImageView(MainActivity.this);
        stampView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        stampView.setOnViewActiveListener(stampActiveListener);
        stampView.setPadding(stampPadding, stampPadding, stampPadding, stampPadding);
        if (bitmap != null) {
            stampView.setImageBitmap(bitmap);
        } else if (!TextUtils.isEmpty(url)) {
            Glide.with(this)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .into(new ImageViewTarget<GlideDrawable>(stampView) {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            super.onResourceReady(resource, glideAnimation);
                        }

                        @Override
                        protected void setResource(GlideDrawable resource) {
                            getView().setImageDrawable(resource);
                        }
                    });
        }
        stampView.setExternalOnTouchListener(stampTouchListener);
        stampView.setOnTouchListener(imageSelectorTouchListener);
        stampView.setTag(R.id.stamp_content, contentId);
        return stampView;
    }

    private View.OnTouchListener imageSelectorTouchListener = (v, event) -> {
        if (v instanceof ImageView) {
            Logger.d(TAG, "Ev: " + event.getActionMasked());
            ImageView touchedImageView = (ImageView) v;
            if (touchedImageView.getDrawable() != null) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN:
                        touchedImageView.getDrawable().setColorFilter(selectedItemFilterColor);
                        break;
                    default:
                        touchedImageView.getDrawable().setColorFilter(null);
                }
            }
        }
        return false;
    };

    private boolean isRemoveStampButtonHighlighted;
    private View.OnTouchListener stampTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (v instanceof StampImageView) {
                if (Utils.isPointInsideView(event.getRawX(), event.getRawY(), stampsButton)) {
                    if (!isRemoveStampButtonHighlighted) {
                        stampsButton.animate().scaleX(1.2f).scaleY(1.2f).setDuration(200).start();
                        v.setAlpha(0.3f);
                        isRemoveStampButtonHighlighted = true;
                    }
                    if (event.getActionMasked() == MotionEvent.ACTION_UP) {
                        removeStamp((StampImageView) v);
                    }
                } else {
                    if (isRemoveStampButtonHighlighted) {
                        stampsButton.animate().scaleX(1).scaleY(1).setDuration(200).start();
                        v.setAlpha(1);
                        isRemoveStampButtonHighlighted = false;
                    }
                }
            }
            return false;
        }
    };

    private void removeStamp(StampImageView stamp) {
        stamps.remove(stamp);
        stampsContainer.removeView(stamp);
    }

    private boolean isStampActive;
    private StampImageView.OnStampViewActiveListener stampActiveListener = new StampImageView.OnStampViewActiveListener() {
        @Override
        public void onViewActive(StampImageView view, boolean isActive) {
            if (isActive) {
                isStampActive = true;
                stamps.remove(view);
                stamps.addLast(view);
                view.bringToFront();
                stampsContainer.invalidate();
                stampsButton.setImageResource(R.drawable.ic_delete);
                setStampsLockedStatusExceptGiven(view, true);
            } else {
                isStampActive = false;
                stampsButton.setImageResource(R.drawable.ic_emoticon);
                setStampsLockedStatusExceptGiven(view, false);
            }
        }
    };

    private void setStampsLockedStatusExceptGiven(StampImageView view, boolean isLocked) {
        for (StampImageView stampView : stamps) {
            if (!stampView.equals(view)) {
                stampView.setLocked(isLocked);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SELECT_PHOTO_CODE) {
            setProgressVisible(true);
            clearStamps();
            try {
                showImageByUri(data.getData());
            } catch (IOException e) {
                showMessage(R.string.failed_to_load_photo);
            }
        } else {
            showMessage(R.string.failed_to_load_photo);
            setProgressVisible(false);
        }
    }

    private void showImageByUri(Uri imageUri) throws IOException {
        InputStream imageStream = getContentResolver().openInputStream(imageUri);
        showImageFromInputStream(imageStream);
    }

    private void showImageFromInputStream(InputStream imageStream) throws IOException {
        Bitmap sourceBitmap = BitmapFactory.decodeStream(imageStream);
        Bitmap croppedBitmap = cropBitmapToScreen(sourceBitmap);
        Utils.compressBitmapToFile(croppedBitmap, photoFile, 100);
        switchToEditMode();
    }

    private void clearStamps() {
        stamps.clear();
        stampsContainer.removeAllViews();
    }

    private void toggleStampsFrame() {
        final boolean isSetVisible = !isStampsListOpen();
        if (isSetVisible) {
            stampsFrame.setAlpha(0);
        }
        stampsFrame.animate()
                .alpha(isSetVisible ? 1 : 0)
                .setDuration(200)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        stampsFrame.setVisibility(View.VISIBLE);
                        if (isSetVisible) {
                            shareButtonContainer.setVisibility(View.GONE);
                            backToStoriesButton.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (!isSetVisible) {
                            stampsFrame.setVisibility(View.GONE);
                            shareButtonContainer.setVisibility(View.VISIBLE);
                            backToStoriesButton.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .start();
    }

    private void switchToEditMode() {
        // hide photo taking buttons on start
        setTakePhotoButtonsBlockVisible(false);
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                Bitmap bitmap = Utils.imageOrientationValidator(BitmapFactory.decodeFile(photoFile.getAbsolutePath()), photoFile.getAbsolutePath());
                if (bitmap != null) {
                    isInEditMode = true;
                    if (useHorizontalFlip
                            || bitmap.getWidth() != Utils.getScreenWidthInPx(MainActivity.this)
                            || bitmap.getHeight() != Utils.getScreenHeightInPx(MainActivity.this)) {
                        Matrix matrix = new Matrix();
                        int width = bitmap.getWidth();
                        int height = bitmap.getHeight();
                        float sx = Utils.getScreenWidthInPx(MainActivity.this) / (float) width;
                        float sy = Utils.getScreenHeightInPx(MainActivity.this) / (float) height;
                        if (useHorizontalFlip) {
                            sx *= -1;
                            useHorizontalFlip = false;
                        }
                        matrix.setScale(sx, sy);
                        Bitmap sourceBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);

                        photoBitmap = applyOverlayBlend(sourceBitmap);
                        publishProgress();
                        try {
                            Utils.compressBitmapToFile(photoBitmap, photoFile, 100);
                        } catch (IOException e) {
                            Logger.e(TAG, e);
                        }
                    } else {
                        photoBitmap = applyOverlayBlend(bitmap);
                        publishProgress();
                    }
                    detectFaces(photoBitmap);
                } else {
                    showMessage(R.string.failed_to_load_photo);
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(Void... values) {
                photoImageView.setImageBitmap(photoBitmap);
                photoImageView.setVisibility(View.VISIBLE);
                // return take photo button to normal state
                takePhotoButton.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                closeButton.setVisibility(View.VISIBLE);
                setProgressVisible(false);
                editPhotoButtonsContainer.setVisibility(View.VISIBLE);
                buildStoriesPager();
                if (stories.size() > 1) {
                    applyStory(stories.get(1).getStoryId());
                    rv.postDelayed(() -> scrollListToPosition(rv, 1, false), 100);
                }
                setStoriesActive(true);
            }
        }.execute();

    }

    private Bitmap applyOverlayBlend(Bitmap sourceBitmap) {
        Bitmap bluredBitmap = Utils.blurRenderScript(MainActivity.this, sourceBitmap, 4);
        Bitmap result = Utils.applyBlend(sourceBitmap, bluredBitmap, Utils.KEYS_BLEND_DESOLVE_OVERLAY);
        sourceBitmap.recycle();
        bluredBitmap.recycle();
        return result;
    }

    private void setStoriesActive(boolean active) {
        storiesContainer.setTranslationY(active ? buttonsTranslation : 0);
        storiesContainer
                .animate()
                .translationY(active ? 0 : buttonsTranslation)
                .setDuration(200)
                .setListener(new SimpleAnimationListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        storiesContainer.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (!active) {
                            storiesContainer.setVisibility(View.GONE);
                        }
                    }
                })
                .start();

        shareButtonContainer.animate().translationX(active ? buttonsTranslation : 0).setDuration(200).start();
        stampsButton.animate().translationX(active ? buttonsTranslation : 0).setDuration(200).start();
        backToStoriesButton.animate().translationX(active ? -buttonsTranslation : 0).setDuration(200).start();
    }

    private void detectFaces(Bitmap bitmap) {
        FaceDetector detector = new FaceDetector.Builder(getApplicationContext())
                .setTrackingEnabled(false)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .setMode(FaceDetector.FAST_MODE)
                .setProminentFaceOnly(true)
                .setMinFaceSize(0.35f)
                .build();
        Detector<Face> safeDetector = new SafeFaceDetector(detector);
        // Create a frame from the bitmap and run face detection on the frame.
        Frame frame = new Frame.Builder().setBitmap(bitmap).build();
        SparseArray<Face> faces = safeDetector.detect(frame);
//        overlay.post(() -> overlay.setContent(faces));
        currentFace = null;
        for (int i = 0; i < faces.size(); ++i) {
            Face face = faces.valueAt(i);
            if (face != null) {
                currentFace = face;
                break;
            }
        }
        safeDetector.release();
    }

    private Bitmap cropBitmapToScreen(Bitmap source) {
        return Utils.scaleCenterCrop(source, Utils.getScreenHeightInPx(this), Utils.getScreenWidthInPx(this));
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(mSurfaceView.getHolder());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (mCamera != null) {
            startCamera();
        }
    }

    private void startCamera() {
        Camera.Parameters params = mCamera.getParameters();
        List<Camera.Size> sizes = params.getSupportedPreviewSizes();
        Camera.Size optimalSize = Utils.getOptimalCameraSize(sizes, Utils.getScreenWidthInPx(this), Utils.getScreenHeightInPx(this));
        params.setPreviewSize(optimalSize.width, optimalSize.height);
        params.setPictureSize(optimalSize.width, optimalSize.height);
        mCamera.setParameters(params);


        Camera.CameraInfo camInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(currentCameraId, camInfo);
        int cameraRotationOffset = camInfo.orientation;

        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break; // Natural orientation
            case Surface.ROTATION_90:
                degrees = 90;
                break; // Landscape left
            case Surface.ROTATION_180:
                degrees = 180;
                break;// Upside down
            case Surface.ROTATION_270:
                degrees = 270;
                break;// Landscape right
        }
        int displayRotation;
        if (isFrontCam()) {
            displayRotation = (cameraRotationOffset + degrees) % 360;
            displayRotation = (360 - displayRotation) % 360;
        } else {
            displayRotation = (cameraRotationOffset - degrees + 360) % 360;
        }

        mCamera.setDisplayOrientation(displayRotation);
        mCamera.startPreview();
        // xxx Hack to prevent crash
        try {
            mCamera.autoFocus(autoFocusCallback);
        } catch (RuntimeException e) {
            // ¯\_(ツ)_/¯
        }
    }

    private Camera.AutoFocusCallback autoFocusCallback = new Camera.AutoFocusCallback() {
        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            mCamera.cancelAutoFocus();
        }
    };

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        mCamera.stopPreview();
        try {
            FileOutputStream outStream = new FileOutputStream(photoFile);
            outStream.write(data);
            outStream.flush();
            outStream.close();
            if (isFrontCam()) {
                useHorizontalFlip = true;
            }
            ExifInterface exifi = new ExifInterface(photoFile.getAbsolutePath());
            int rotation = ExifInterface.ORIENTATION_NORMAL;
            switch (getCurrentCameraInfo().orientation) {
                case 0:
                    rotation = ExifInterface.ORIENTATION_NORMAL;
                    break;
                case 90:
                    rotation = ExifInterface.ORIENTATION_ROTATE_90;
                    break;
                case 180:
                    rotation = ExifInterface.ORIENTATION_ROTATE_90;
                    break;
                case 270:
                    rotation = ExifInterface.ORIENTATION_ROTATE_270;
                    break;
            }
            try {
                exifi.setAttribute(ExifInterface.TAG_ORIENTATION, String.valueOf(rotation));
                exifi.saveAttributes();
            } catch (IOException ignored) {
            }
        } catch (IOException e) {
            showMessage(R.string.failed_to_create_image);
            return;
        }
        switchToEditMode();
        mCamera.startPreview();
    }

    private Camera.CameraInfo getCurrentCameraInfo() {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(currentCameraId, cameraInfo);
        return cameraInfo;
    }

    private void showMessage(@StringRes int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShutter() {
        setProgressVisible(true);
    }

    private void setProgressVisible(boolean isVisible) {
        progress.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    private boolean isFrontCam() {
        return currentCameraId == Camera.CameraInfo.CAMERA_FACING_FRONT;
    }

    @Override
    public void onBackPressed() {
        if (isStampsListOpen()) {
            toggleStampsFrame();
        } else if (isInEditMode) {
            exitEditMode(false);
        } else {
            super.onBackPressed();
        }
    }

    // -----------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------
    // --------------------------------------- STAMP POSITION METHODS --------------------------------------
    // -----------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------


    private boolean positionStamp(StampImageView stampView, StoryStampsBean stampBean) {
        boolean result = false;
        if (!TextUtils.isEmpty(stampBean.getType())) {
            switch (stampBean.getType()) {
                case "static":
                    result = positionStaticStamp(stampView, stampBean);
                    break;
                case "mouth":
                    result = positionMouthStamp(stampView, stampBean);
                    break;
                case "eyes":
                    result = positionEyesStamp(stampView, stampBean);
                    break;
                case "frame":
                    result = positionFrameStamp(stampView, stampBean);
                default:
            }
        }
        return result;
    }

    private boolean positionFrameStamp(StampImageView stampView, StoryStampsBean stampBean) {
        float scale = (Utils.getScreenWidthInPx(this) / (float) (stampSize - stampPadding * 2));
        stampView.setScale(scale);
        if (stampBean.getPositionType() != null) {
            int stampCenter = stampSize / 2;
            switch (stampBean.getPositionType()) {
                case "t":
                    PointF top = new PointF(stampPadding, stampPadding);
                    PointF scaledTop = Geometry.scalePoint(top.x, top.y, stampCenter, stampCenter, scale);
                    stampView.setX(-scaledTop.x);
                    stampView.setY(-scaledTop.y);
                    return true;
                case "b":
                    PointF bottom = new PointF(stampPadding, stampSize - stampPadding);
                    PointF scaledBottom = Geometry.scalePoint(bottom.x, bottom.y, stampCenter, stampCenter, scale);
                    stampView.setX(-scaledBottom.x);
                    stampView.setY(Utils.getScreenHeightInPx(this) - scaledBottom.y);
                    return true;
                default:
            }
        }
        return false;
    }

    private boolean positionEyesStamp(StampImageView dynamicStamp, StoryStampsBean stampBean) {
        return drawStampByTwoLandmarks(dynamicStamp, getStampDynamicPoints(0), Landmark.RIGHT_EYE, Landmark.LEFT_EYE, stampBean);
    }

    // 0 - eyes
    // 1 - mouth
    private int[] getStampDynamicPoints(int i) {
        int[] coords = new int[4];
        int rawStampSize = stampSize - stampPadding * 2;
        coords[1] = rawStampSize / 2;
        coords[3] = rawStampSize / 2;
        switch (i) {
            case 0:
                coords[0] = rawStampSize / 4;
                coords[2] = (rawStampSize / 4) * 3;
                break;
            case 1:
                coords[0] = 0;
                coords[2] = rawStampSize;
                break;
            default:
        }
        return coords;
    }

    private boolean positionMouthStamp(StampImageView dynamicStamp, StoryStampsBean stampBean) {
        return drawStampByTwoLandmarks(dynamicStamp, getStampDynamicPoints(1), Landmark.RIGHT_MOUTH, Landmark.LEFT_MOUTH, stampBean);
    }

    private boolean drawStampByTwoLandmarks(StampImageView dynamicStamp, int[] coords, int leftLandmark, int rightLandmark, StoryStampsBean stampBean) {
        PointF leftPosition = Utils.getLandmarkPosition(currentFace, leftLandmark);
        PointF rightPosition = Utils.getLandmarkPosition(currentFace, rightLandmark);
        return drawStampByTwoPoints(dynamicStamp, leftPosition, rightPosition, coords, stampBean);
    }

    private boolean drawStampByTwoPoints(StampImageView dynamicStamp, PointF leftPosition, PointF rightPosition, int[] coords, StoryStampsBean stampBean) {
        if (leftPosition != null && rightPosition != null) {
            try {
                PointF stampLPoint = new PointF(coords[0], coords[1]);
                PointF stampRPoint = new PointF(coords[2], coords[3]);
                return positionDynamicStamp(dynamicStamp, stampLPoint, stampRPoint, leftPosition, rightPosition, stampBean);
            } catch (ArrayIndexOutOfBoundsException ex) {
                Logger.w(TAG, "Can't get coordinates");
            }
        }
        return false;
    }

    private boolean positionStaticStamp(StampImageView stampView, StoryStampsBean stampBean) {
        PointF point = new PointF();
        applyStaticPointPosition(point, stampBean.getPositionType());

        float scale = 1;
        int rotation = 0;

        if (stampBean.getScale() != null && stampBean.getScale() != 0) {
            scale *= stampBean.getScale();
        }
        if (stampBean.getRotation() != null) {
            rotation += stampBean.getRotation();
        }

        float screenFactor = Utils.getScreenHeightInPx(this) / (640f * getDensity());
        if (screenFactor != 0) {
            scale *= screenFactor;
        }

//        int stampCenter = (stampSize - stampPadding * 2) / 2;
//        if (rotation > 0) {
//            translationPoint = Geometry.rotatePoint(translationPoint.x, translationPoint.y, stampCenter, stampCenter, 360 - rotation);
//        }
//        if (scale > 0) {
//            translationPoint = Geometry.scalePoint(translationPoint.x, translationPoint.y, stampCenter, stampCenter, scale);
//        }

        int[] coords = getCords(stampBean.getPoints());

        float x = point.x - coords[0] - stampPadding;
        float y = point.y - coords[1] - stampPadding;


        stampView.setScale(scale);
        stampView.setX(x);
        stampView.setY(y);
        stampView.setDrawingRotation(rotation, false);
        return true;

    }

    // TODO remove this
    private int[] getCords(String json) {
        List<StoriesResponse.Point> points = NetworkManager.getInstance().getGson().fromJson(json, new TypeToken<List<StoriesResponse.Point>>() {
        }.getType());
        int density = getDensity();
        int[] result = new int[points.size() * 2];
        for (int i = 0; i < points.size(); i++) {
            result[i * 2] = points.get(i).x * density;
            result[i * 2 + 1] = points.get(i).y * density;
        }
        return result;
    }

    private void applyStaticPointPosition(PointF point, String position) {
        int screenWidth = Utils.getScreenWidthInPx(this);
        int screenHeight = Utils.getScreenHeightInPx(this);
        switch (position) {
            case "tl":
                point.x = 0;
                point.y = 0;
                break;
            case "tc":
                point.x = screenWidth / 2;
                point.y = 0;
                break;
            case "tr":
                point.x = screenWidth;
                point.y = 0;
                break;
            case "ml":
                point.x = 0;
                point.y = screenHeight / 2;
                break;
            case "mc":
                point.x = screenWidth / 2;
                point.y = screenHeight / 2;
                break;
            case "mr":
                point.x = screenWidth;
                point.y = screenHeight / 2;
                break;
            case "bl":
            default:
                point.x = 0;
                point.y = screenHeight;
                break;
            case "bc":
                point.x = screenWidth / 2;
                point.y = screenHeight;
                break;
            case "br":
                point.x = screenWidth;
                point.y = screenHeight;
                break;
        }
    }

    private boolean positionDynamicStamp(StampImageView stampView,
                                         PointF stampPointA, PointF stampPointB,
                                         PointF leftPosition, PointF rightPosition,
                                         StoryStampsBean stampBean) {
        if (rightPosition != null && leftPosition != null) {
            // add padding to points
            stampPointA.x += stampPadding;
            stampPointA.y += stampPadding;
            stampPointB.x += stampPadding;
            stampPointB.y += stampPadding;

            // initial values
            float scale = 1;
            float rotation = 0;
            float X = 0;
            float Y = 0;

            // google vision offsets processing
            float gvScale = Geometry.calculateScaleFactorByTwoLines(stampPointA, stampPointB, leftPosition, rightPosition);
            float gvRotation = Geometry.angleBetween2Lines(stampPointA, stampPointB, leftPosition, rightPosition);
            float targetCenterX = (leftPosition.x + rightPosition.x) / 2;
            float targetCenterY = (leftPosition.y + rightPosition.y) / 2;
            float gvOffsetX = targetCenterX - stampSize / 2;
            float gvOffsetY = targetCenterY - stampSize / 2;

            X += gvOffsetX;
            Y += gvOffsetY;
            scale *= gvScale;
            rotation += gvRotation;

            // troublemaker offsets processing
            StoriesResponse.Point tmOffset = NetworkManager.getInstance().getGson().fromJson(stampBean.getOffset(), StoriesResponse.Point.class);
            if (tmOffset != null) {
                PointF offset = Geometry.rotatePoint(tmOffset.x, tmOffset.y, 0, 0, gvRotation);
                offset.x *= gvScale * getDensity();
                offset.y *= gvScale * getDensity();
                if (stampBean.getScale() != null && stampBean.getScale() != 0) {
                    scale *= stampBean.getScale();
                }
                if (stampBean.getRotation() != null) {
                    rotation -= stampBean.getRotation();
                }
                X += offset.x;
                Y += offset.y;
            }


            // final applying
            stampView.setScale(scale);
            stampView.setDrawingRotation(-rotation, false);
            stampView.setX(X);
            stampView.setY(Y);
            return true;
        }
        return false;
    }

    private int getDensity() {
        if (density == 0) {
            density = getResources().getDisplayMetrics().density;
        }
        if (density > 2.5) {
            return 3;
        } else {
            return (int) density;
        }
    }
}
