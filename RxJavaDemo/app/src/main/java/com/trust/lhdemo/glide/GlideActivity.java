package com.trust.lhdemo.glide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.trust.lhdemo.BaseActivity;
import com.trust.lhdemo.R;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class GlideActivity extends BaseActivity {
    private ImageView imageView,gifImgView,roundImgView,filletImgView,roundGifImgView,
    filletGifImg,blurryImgView,blurrGifImgView,bluerrRoundImgView,bluerrRoundGifImgView,
    bluerrFilletImgView,bluerrFiletGifImgView;

    private   String imgUrl =
            "http://img3.imgtn.bdimg.com/it/u=4087641587,1392272150&fm=23&gp=0.jpg";

    private  String gifUrl =
            "https://a-ssl.duitang.com/uploads/item/201502/22/20150222213238_XRsBY.gif";

    private String roundUrl =
            "http://img3.imgtn.bdimg.com/it/u=3719578386,1729384357&fm=23&gp=0.jpg";

    private String filletUrl =
            "http://img4.imgtn.bdimg.com/it/u=4104794845,2864995351&fm=23&gp=0.jpg";

    private String roundGifUrl =
            "https://a-ssl.duitang.com/uploads/item/201508/28/20150828181611_sP8Vx.gif";

    private String filleGifUrl =
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=" +
                    "1492580767101&di=981aa9ce414b9585d2f25ff141ee2631&imgtype=" +
                    "0&src=http%3A%2F%2Fimg3.duitang.com%2Fuploads%2Fitem%2F201" +
                    "608%2F24%2F20160824202257_xaLum.gif";

    private String blurryUrl =
            "http://2t.5068.com/uploads/allimg/151102/57-1511021S145-53.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        iniView();
        show();
    }

    private void show() {
        //普通img
        Glide.with(this).load(imgUrl).asBitmap().placeholder(R.mipmap.ic_launcher).
                error(R.drawable.error).into(imageView);
        //gif img
        Glide.with(this).load(gifUrl).asGif().placeholder(R.mipmap.ic_launcher)
                .error(R.drawable.error).
                diskCacheStrategy(DiskCacheStrategy.SOURCE).into(gifImgView);
        //圆图
        Glide.with(this).load(roundUrl).placeholder(R.mipmap.ic_launcher)
                .bitmapTransform(new CropCircleTransformation(this))
                .into(roundImgView);
        //圆角图
        Glide.with(this).load(filletUrl).placeholder(R.mipmap.ic_launcher)
                .crossFade(1000).bitmapTransform(
                new RoundedCornersTransformation(this,30,0,RoundedCornersTransformation.
                        CornerType.ALL)).into(filletImgView);
        //圆gif
        Glide.with(this).load(roundGifUrl).placeholder(R.mipmap.ic_launcher)
                .error(R.drawable.error).
                diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .bitmapTransform(new CropCircleTransformation(this))
                .into(roundGifImgView);

        //圆角gif
        Glide.with(this).load(filleGifUrl).placeholder(R.mipmap.ic_launcher)
                .error(R.drawable.error).
                diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .crossFade(1000).bitmapTransform(
                new RoundedCornersTransformation(this,30,0,RoundedCornersTransformation.
                        CornerType.ALL)).into(filletGifImg);

        //高斯模糊
        Glide.with(this).load(imgUrl).placeholder(R.mipmap.ic_launcher)
                .bitmapTransform(new BlurTransformation(this,23,1))
        .into(blurryImgView);

        //高斯gif
        Glide.with(this).load(gifUrl).placeholder(R.mipmap.ic_launcher)
                .error(R.drawable.error).
                diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .bitmapTransform(new BlurTransformation(this,23,1))
                .into(blurrGifImgView);

        //高斯圆图
        Glide.with(this).load("").placeholder(R.mipmap.ic_launcher)
                .into(bluerrRoundImgView);

        //高斯圆gif
        Glide.with(this).load("").placeholder(R.mipmap.ic_launcher)
                .into(bluerrRoundGifImgView);

        //高斯圆角
        Glide.with(this).load("").placeholder(R.mipmap.ic_launcher)
                .into(bluerrFilletImgView);

        //高斯圆角gif
        Glide.with(this).load("").placeholder(R.mipmap.ic_launcher)
                .into(bluerrFiletGifImgView);


    }

    private void iniView() {
        imageView = (ImageView) findViewById(R.id.activity_glide_img);
        gifImgView = (ImageView) findViewById(R.id.activity_glide_gif_img);
        roundImgView = (ImageView) findViewById(R.id.activity_glide_round_img);
        filletImgView = (ImageView) findViewById(R.id.activity_glide_fillet_img);
        roundGifImgView = (ImageView) findViewById(R.id.activity_glide_round_gif_img);
        filletGifImg = (ImageView) findViewById(R.id.activity_glide_fillet_gif_img);
        blurryImgView = (ImageView) findViewById(R.id.activity_glide_blurry_img);
        blurrGifImgView = (ImageView) findViewById(R.id.activity_glide_blurry_gif_img);
        bluerrRoundImgView = (ImageView) findViewById(R.id.activity_glide_blurry_round_img);
        bluerrRoundGifImgView = (ImageView) findViewById(R.id.activity_glide_blurry_round_gif_img);
        bluerrFilletImgView = (ImageView) findViewById(R.id.activity_glide_blurry_fillet_img);
        bluerrFiletGifImgView = (ImageView) findViewById(R.id.activity_glide_blurry_fillet_gif_img);
    }
}
