package com.williamlu.widgetlib.banner;

import androidx.viewpager.widget.ViewPager;

import com.williamlu.widgetlib.banner.transformer.AccordionTransformer;
import com.williamlu.widgetlib.banner.transformer.BackgroundToForegroundTransformer;
import com.williamlu.widgetlib.banner.transformer.CubeInTransformer;
import com.williamlu.widgetlib.banner.transformer.CubeOutTransformer;
import com.williamlu.widgetlib.banner.transformer.DefaultTransformer;
import com.williamlu.widgetlib.banner.transformer.DepthPageTransformer;
import com.williamlu.widgetlib.banner.transformer.FlipHorizontalTransformer;
import com.williamlu.widgetlib.banner.transformer.FlipVerticalTransformer;
import com.williamlu.widgetlib.banner.transformer.ForegroundToBackgroundTransformer;
import com.williamlu.widgetlib.banner.transformer.RotateDownTransformer;
import com.williamlu.widgetlib.banner.transformer.RotateUpTransformer;
import com.williamlu.widgetlib.banner.transformer.ScaleInOutTransformer;
import com.williamlu.widgetlib.banner.transformer.StackTransformer;
import com.williamlu.widgetlib.banner.transformer.TabletTransformer;
import com.williamlu.widgetlib.banner.transformer.ZoomInTransformer;
import com.williamlu.widgetlib.banner.transformer.ZoomOutSlideTransformer;
import com.williamlu.widgetlib.banner.transformer.ZoomOutTranformer;

public class Transformer {
    public static Class<? extends ViewPager.PageTransformer> Default                = DefaultTransformer.class;
    public static Class<? extends ViewPager.PageTransformer>           Accordion              = AccordionTransformer.class;
    public static Class<? extends ViewPager.PageTransformer>           BackgroundToForeground = BackgroundToForegroundTransformer.class;
    public static Class<? extends ViewPager.PageTransformer>           ForegroundToBackground = ForegroundToBackgroundTransformer.class;
    public static Class<? extends ViewPager.PageTransformer>           CubeIn                 = CubeInTransformer.class;
    public static Class<? extends ViewPager.PageTransformer>           CubeOut                = CubeOutTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> DepthPage = DepthPageTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> FlipHorizontal = FlipHorizontalTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> FlipVertical = FlipVerticalTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> RotateDown = RotateDownTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> RotateUp = RotateUpTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> ScaleInOut = ScaleInOutTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> Stack = StackTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> Tablet = TabletTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> ZoomIn = ZoomInTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> ZoomOut = ZoomOutTranformer.class;
    public static Class<? extends ViewPager.PageTransformer> ZoomOutSlide = ZoomOutSlideTransformer.class;
}
