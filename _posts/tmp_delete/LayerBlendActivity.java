public class LayerBlendActivity extends ImageToolBaseActivity{
    private static final String TAG = "LayerBlendActivity";

    private List<LayerBlend> mLayerBlendList = new ArrayList<LayerBlend>(){
        {
            add(new LayerBlend("原图", 0, 0));
            add(new LayerBlend("正常", 1, 2));
            add(new LayerBlend("溶解", 2, 2));
            add(new LayerBlend("变暗", 3, 2));
            add(new LayerBlend("正片叠底", 4, 2));
            add(new LayerBlend("颜色加深", 5, 2));
            add(new LayerBlend("线性加深", 6, 2));
            add(new LayerBlend("深色", 7, 2));
            add(new LayerBlend("变亮", 8, 2));
            add(new LayerBlend("滤色", 9, 2));
            add(new LayerBlend("颜色减淡", 10, 2));
            add(new LayerBlend("线性减淡(添加)", 11, 2));
            add(new LayerBlend("浅色", 12, 2));
            add(new LayerBlend("叠加", 13, 2));
            add(new LayerBlend("柔光", 14, 2));
            add(new LayerBlend("强光", 15, 2));
            add(new LayerBlend("亮光", 16, 2));
            add(new LayerBlend("线性光", 17, 2));
            add(new LayerBlend("点光", 18, 2));
            add(new LayerBlend("实色混合", 19, 2));
            add(new LayerBlend("差值", 20, 2));
            add(new LayerBlend("排除", 21, 2));
            add(new LayerBlend("减去", 22, 2));
            add(new LayerBlend("划分", 23, 2));
            add(new LayerBlend("色相", 24, 2));
            add(new LayerBlend("饱和度", 25, 2));
            add(new LayerBlend("颜色", 26, 2));
            add(new LayerBlend("明度", 27, 2));

        }
    };

    public LayerBlendActivity() {
        title = "图层融合";
        showSliderMenu = false;
        mEffectParam = new LayerBlendToolboxParam();
        bottomBarChildLayoutId = R.layout.bottom_bar_content_layerblend;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((LayerBlendToolboxParam)mEffectParam).setIsNewMask(1);
        ((LayerBlendToolboxParam)mEffectParam).setMaskBmp(ImageUtils.getBitmapFromAssets(this, "img_test/img_1.jpg"));
    }

    private TransformController mTransformController = null;

    @Override
    protected void userHandleMessage(Message msg) {
        super.userHandleMessage(msg);
        if(msg.what == MSG_RENDER_THREAD_LAUNCH_FINISHED){
            Log.d(TAG, "userHandleMessage: ");
            mTransformController = new TransformController(mDisplayRect);
            mGLSurfaceViewEngine.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    //if(event.getPointerCount() == 2){
                        mTransformController.onFingerTouchChanged(v, event);
                        ((LayerBlendToolboxParam)mEffectParam).setTransformParam(mTransformController.getOffsetX(), mTransformController.getOffsetY(),
                                mTransformController.getScaleXY(), mTransformController.getRotateZ());
                        render();
                    //}
                    return true;
                }
            });
        }
    }

    @Override
    public void onSetupWidgets(View parentView, View bottomBarView) {
        super.onSetupWidgets(parentView, bottomBarView);
        RecyclerView recyclerView = (RecyclerView) bottomBarView.findViewById(R.id.recyclerview_layerblend);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        LayerBlendAdapter layerBlendAdapter = new LayerBlendAdapter(mLayerBlendList);
        layerBlendAdapter.setOnItemClickListener(new LayerBlendAdapter.OnItemClickListener() {
            @Override
            public void onclick(int blendMode, int maskCoordMode) {
                ((LayerBlendToolboxParam)mEffectParam).setBlendMode(blendMode);
                ((LayerBlendToolboxParam)mEffectParam).setMaskCoordMode(maskCoordMode);
                render();
            }
        });
        recyclerView.setAdapter(layerBlendAdapter);
    }



    @Override
    public void render() {
        mGLSurfaceViewEngine.setEffectParam(mEffectParam.getTypeId(), ((LayerBlendToolboxParam)mEffectParam).getParam());
        mGLSurfaceViewEngine.requestRender();
    }

    @Override
    public Bitmap onSaveEffect(Bitmap bitmap) {
        return null;
    }
}
