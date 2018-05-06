package co.bxvip.android.commonlib.ui

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.annotation.RestrictTo
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.RelativeLayout.*
import co.bxvip.android.commonlib.widget.R
import co.bxvip.tools.DisplayUtils.getStatusBarHeight
import co.bxvip.wedgit.StatusBarView
import co.bxvip.wedgit.drawable.TitleArrowDrawable
import com.qihoo360.replugin.RePlugin


/**
 * <pre>
 *     author: vic
 *     time  : 18-1-14
 *     desc  : 公共头部
 * </pre>
 */

class TitleBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {
    private var leftTextVisible = View.VISIBLE
    private var leftText: CharSequence = ""
    private var leftTextSize = 16f
    private var leftTextColor = 0xffffffff.toInt()
    private var leftImage = 0  // todo
    private var leftImageVisible = View.VISIBLE
    private var titleText: CharSequence = ""
    private var titleTextSize = 18f
    private var titleTextColor = 0xffffffff.toInt()
    private var titleVisible = View.VISIBLE
    private var titleRightImg = -1
    private var rightImage = 0
    private var rightImageVisible = View.VISIBLE
    private var rightTextVisible = View.VISIBLE
    private var rightText: CharSequence = ""
    private var rightTextSize = 16f
    private var rightTextColor = 0xffffffff.toInt()
    private var titleScrllo = false
    private var titleBarLayoutId = -1

    private var titleBarHeight = 46f
    /* -------------初始化UI----------------- */

    private val leftImgView by lazy {
        ImageView(context).apply {
            layoutParams = RelativeLayout.LayoutParams(dip2px(44f), ViewGroup.LayoutParams.MATCH_PARENT).apply {
                addRule(CENTER_VERTICAL)
                leftMargin = dip2px(5f)
            }
            setPadding(dip2px(5f), dip2px(5f), dip2px(5f), dip2px(8f))
            scaleType = ImageView.ScaleType.CENTER_INSIDE
            visibility = leftImageVisible
            if (leftImage == 0) {
                setImageDrawable(TitleArrowDrawable())
            } else {
                setImageResource(leftImage)
            }
        }
    }

    private val leftTextView by lazy {
        TextView(context).apply {
            layoutParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT).apply {
                addRule(CENTER_VERTICAL)
            }
            text = leftText
            gravity = Gravity.CENTER
            setTextColor(leftTextColor)
            setPadding(dip2px(15f), 0, dip2px(15f), 0)
            setTextSize(TypedValue.COMPLEX_UNIT_SP, leftTextSize)
            visibility = leftTextVisible
        }
    }
    // 标题
    private val titleTextView by lazy {
        TitleTextView(context).apply {
            layoutParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
                addRule(CENTER_IN_PARENT)
            }
            text = titleText
            maxEms = 8
            //android:ellipsize="end"
            ellipsize = TextUtils.TruncateAt.END
            gravity = Gravity.CENTER
            setTextSize(TypedValue.COMPLEX_UNIT_SP, titleTextSize)
            visibility = titleVisible
            setTextColor(titleTextColor)
            setSingleLine()
            if (titleScrllo) {
                ellipsize = TextUtils.TruncateAt.MARQUEE
                marqueeRepeatLimit = -1
                isFocusable = true
                setHorizontallyScrolling(true)
                isFocusableInTouchMode = true
            }
            if (titleRightImg > -1) {
                val drawable = context.resources.getDrawable(titleRightImg)
                drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
                setCompoundDrawables(null, null, drawable, null)
                compoundDrawablePadding = dip2px(5f)
            }
        }
    }
    private val rightTextView by lazy {
        TextView(context).apply {
            layoutParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT).apply {
                addRule(CENTER_VERTICAL)
                addRule(ALIGN_PARENT_RIGHT)
            }
            text = rightText
            gravity = Gravity.CENTER
            setTextColor(rightTextColor)
            visibility = rightTextVisible
            setPadding(dip2px(15f), 0, dip2px(15f), 0)
            setTextSize(TypedValue.COMPLEX_UNIT_SP, rightTextSize)
        }
    }

    private val rightImgView by lazy {
        ImageView(context).apply {
            layoutParams = RelativeLayout.LayoutParams((dip2px(titleBarHeight) * 0.7).toInt(), (dip2px(titleBarHeight) * 0.7).toInt()).apply {
                addRule(CENTER_VERTICAL)
                addRule(ALIGN_PARENT_RIGHT)
                rightMargin = dip2px(10f)
            }
            setImageResource(rightImage)
            setPadding(dip2px(5f), dip2px(5f), dip2px(5f), dip2px(5f))
            visibility = rightImageVisible
            scaleType = ImageView.ScaleType.CENTER_INSIDE
        }
    }

    private val RootLayout by lazy {
        LinearLayout(context).apply {
            layoutParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            orientation = LinearLayout.VERTICAL
        }
    }


    private val relativeLayout by lazy {
        RelativeLayout(context).apply {
            layoutParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }
    }

    // 状态栏背景

    private val statuBar by lazy {
        StatusBarView(context).apply {

            var drawable = fetchRes(this, "co.bxvip.android.plugin.skin", "drawable", "home_head_1")
            if (drawable != null) {
                setBackgroundDrawable(drawable)
            } else {
                try {
                    drawable = context.resources.getDrawable(context.resources.getIdentifier("home_head_1", "drawable", context.packageName))
                } catch (e: Resources.NotFoundException) {
//                e.printStackTrace()
                }
                if (drawable == null)
                    try {
                        drawable = context.resources.getDrawable(context.resources.getIdentifier("home_head_1", "mipmap", context.packageName))
                    } catch (e: Resources.NotFoundException) {
//                    e.printStackTrace()
                    }
                if (drawable != null) {
                    setBackgroundDrawable(drawable)

                } else {
                    setBackgroundColor(Color.parseColor("#f96716"))
                }
            }
        }
    }

    private val titleBarCenterView by lazy {
        RelativeLayout(context).apply {
            layoutParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT).apply {
                addRule(CENTER_IN_PARENT)
            }
            setPadding(dip2px(40f), dip2px(3f), dip2px(40f), dip2px(3f))
            setBackgroundColor(Color.TRANSPARENT)
            gravity = Gravity.CENTER_HORIZONTAL
            visibility = View.GONE
            if (titleBarLayoutId != -1) {
                visibility = View.VISIBLE
                addView(View.inflate(context, titleBarLayoutId, null), RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT))
            }
        }
    }

    init {
        val array = getContext().obtainStyledAttributes(attrs, R.styleable.TitleBar)
        leftTextVisible = if (array.getBoolean(R.styleable.TitleBar_leftTextVisible, false)) View.VISIBLE else View.GONE
        leftText = array.getText(R.styleable.TitleBar_leftText) ?: "Left"
        leftTextSize = array.getDimensionPixelSize(R.styleable.TitleBar_leftTextSize, leftTextSize.toInt()).toFloat()
        leftTextColor = array.getColor(R.styleable.TitleBar_leftTextColor, 0xffffffff.toInt())

        leftImageVisible = if (array.getBoolean(R.styleable.TitleBar_leftImgVisible, false)) View.VISIBLE else View.GONE
        leftImage = array.getResourceId(R.styleable.TitleBar_leftImg, leftImage)

        titleText = array.getText(R.styleable.TitleBar_titleText) ?: ""
        titleTextSize = array.getDimensionPixelSize(R.styleable.TitleBar_titleTextSize, titleTextSize.toInt()).toFloat()
        titleTextColor = array.getColor(R.styleable.TitleBar_titleTextColor, 0xffffffff.toInt())
        titleVisible = if (array.getBoolean(R.styleable.TitleBar_titleVisible, true)) View.VISIBLE else View.GONE
        titleRightImg = array.getResourceId(R.styleable.TitleBar_titleRightImg, -1)

        rightImageVisible = if (array.getBoolean(R.styleable.TitleBar_rightImgVisible, false)) View.VISIBLE else View.GONE
        rightImage = array.getResourceId(R.styleable.TitleBar_rightImg, 0)

        rightTextVisible = if (array.getBoolean(R.styleable.TitleBar_rightTextVisible, false)) View.VISIBLE else View.GONE
        rightText = array.getText(R.styleable.TitleBar_rightText) ?: "Right"
        rightTextSize = array.getDimensionPixelSize(R.styleable.TitleBar_rightTextSize, rightTextSize.toInt()).toFloat()
        rightTextColor = array.getColor(R.styleable.TitleBar_rightTextColor, 0xffffffff.toInt())

        titleScrllo = array.getBoolean(R.styleable.TitleBar_titleScroll, false)

        titleBarLayoutId = array.getResourceId(R.styleable.TitleBar_titleBarLayoutId, -1)

        array.recycle()
        InitUI()
    }

    private fun InitUI() {
        /* --------组合在一起---------- */

        relativeLayout.addView(leftImgView)
        relativeLayout.addView(leftTextView)
        relativeLayout.addView(titleTextView)
        relativeLayout.addView(rightTextView)
        relativeLayout.addView(rightImgView)
        relativeLayout.addView(titleBarCenterView)
        RootLayout.addView(statuBar)
        RootLayout.addView(relativeLayout)
        val paramLayout = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px(titleBarHeight) + getStatusBarHeight(context, 0))
        this.addView(RootLayout, paramLayout)

        var drawable = fetchRes(relativeLayout, "co.bxvip.android.plugin.skin", "drawable", "home_head")
        if (drawable != null) {
            relativeLayout.setBackgroundDrawable(drawable)
            return
        } else {
            try {
                drawable = context.resources.getDrawable(context.resources.getIdentifier("home_head", "drawable", context.packageName))
            } catch (e: Resources.NotFoundException) {
//                e.printStackTrace()
            }
            if (drawable == null)
                try {
                    drawable = context.resources.getDrawable(context.resources.getIdentifier("home_head", "mipmap", context.packageName))
                } catch (e: Resources.NotFoundException) {
//                    e.printStackTrace()
                }
            if (drawable != null) {
                relativeLayout.setBackgroundDrawable(drawable)
                return
            }

        }
        relativeLayout.setBackgroundColor(Color.parseColor("#fd7c34"))
    }

    private fun fetchRes(view: View, pluginName: String, defType: String, name: String): Drawable? {
        //获取插件中的图片资源
        try {
            val fetchResources = RePlugin.fetchResources(pluginName)
            //获取“插件”加载类
            val classLoader = RePlugin.fetchClassLoader(pluginName)
            //获取“插件”中values/strings.xml对应的类
            val loadClass = classLoader.loadClass("$pluginName.R\$$defType")
            //获取资源名字app_name
            val field = loadClass.getField(name)
            //获取“插件”ID R.string.app_name
            val identifier = field.get(loadClass) as Int
            if (identifier == 0) return null
            return fetchResources.getDrawable(identifier)
        } catch (e: ClassNotFoundException) {
            //e.printStackTrace()
        } catch (e: IllegalAccessException) {
//            e.printStackTrace()
        } catch (e: Exception) {
//            e.printStackTrace()
        }

        return null
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // 父容器传过来的宽度的值
        val viewWidth = View.MeasureSpec.getSize(widthMeasureSpec) - paddingLeft - paddingRight

        if (titleScrllo) {
            val layoutParams = titleTextView.layoutParams
            layoutParams.width = viewWidth * 4 / 9
            titleTextView.layoutParams = layoutParams
        }
        super.onMeasure(widthMeasureSpec, dip2px(titleBarHeight) + getStatusBarHeight(context, 0))
    }

    /********left**title**right****************text view  start****************************/

    /**
     * 设置左边标题的文字
     */
    fun setLeftText(leftText: CharSequence) {
        this.leftText = leftText
        this.leftTextView.text = leftText
    }

    /**
     * 设置lefttext color
     */
    fun setLeftTextColor(leftTextColor: Int) {
        this.leftTextColor = leftTextColor
        this.leftTextView.setTextColor(leftTextColor)
    }

    /**
     * 默认单位SP

     * @param leftTextSize
     */
    fun setLeftTextSize(leftTextSize: Float?) {
        this.leftTextSize = leftTextSize!!
        this.leftTextView.textSize = leftTextSize
    }

    /**
     * 设置左边文字是否可见-
     */
    fun setLeftTextVisible(leftTextVisible: Int) {
        this.leftTextVisible = leftTextVisible
        this.leftTextView.visibility = leftTextVisible
    }

    /**
     * 设置titleView 文字
     */
    fun setTitleText(titleText: CharSequence) {
        this.titleText = titleText
        this.titleTextView.text = titleText
    }

    /**
     * @param lRes titleview 左边的图片 最好是24dp
     * @param rRes titleview 右边的图片 最好是24dp
     * @param drawablePadding 图片和文字的显示的间距
     */
    fun setTitleText(titleText: CharSequence, lRes: Int = 0, rRes: Int = 0, drawablePadding: Int = 3) {
        setTitleText(titleText)
        this.titleTextView.compoundDrawablePadding = dip2px(drawablePadding.toFloat())
        this.titleTextView.setCompoundDrawablesWithIntrinsicBounds(lRes, 0, rRes, 0)
    }

    /**
     * 默认单位SP

     * @param titleTextSize
     */
    fun setTitleTextSize(titleTextSize: Float) {
        this.titleTextSize = titleTextSize
        this.titleTextView.textSize = titleTextSize
    }

    /**
     * 标题文字颜色
     */
    fun setTitleTextColor(titleTextColor: Int) {
        this.titleTextColor = titleTextColor
        this.titleTextView.setTextColor(titleTextColor)
    }

    /**
     * 标题文字是否可见
     */
    fun setTitleVisible(titleVisible: Int) {
        this.titleVisible = titleVisible
        this.titleTextView.visibility = titleVisible
    }

    /**
     * 右边 文字
     */
    fun setRightText(rightText: CharSequence) {
        this.rightText = rightText
        this.rightTextView.text = rightText
    }

    /**
     * 右边文字是否可见
     */

    fun setRightTextVisible(rightTextVisible: Int) {
        this.rightTextVisible = rightTextVisible
        this.rightTextView.visibility = rightTextVisible
    }

    /**
     * @param lRes rightText 左边的图片 最好是24dp
     * @param rRes rightText 右边的图片 最好是24dp
     * @param drawablePadding 图片和文字的显示的间距
     */
    fun setRightText(rightText: CharSequence = this.rightText, lRes: Int = 0, rRes: Int = 0, drawablePadding: Int = 3) {
        setRightText(rightText)
        this.rightTextView.compoundDrawablePadding = dip2px(drawablePadding.toFloat())
        this.rightTextView.setCompoundDrawablesWithIntrinsicBounds(lRes, 0, rRes, 0)
    }


    /**
     * 默认单位SP

     * @param rightTextSize
     */
    fun setRightTextSize(rightTextSize: Float) {
        this.rightTextSize = rightTextSize
    }

    fun setRightTextColor(rightTextColor: Int) {
        this.rightTextColor = rightTextColor
    }

    /********left**title**right****************text view  end****************************/

    /********left**title**right****************image view  start****************************/
    /**
     * 设置左边的单纯图片
     */
    fun setLeftImage(leftImage: Int) {
        this.leftImage = leftImage
        this.leftImgView.setImageResource(leftImage)
    }

    /**
     * 设置左边的图片 可见
     */
    fun setLeftImageVisible(leftImageVisible: Int) {
        this.leftImageVisible = leftImageVisible
        this.leftImgView.visibility = leftImageVisible
    }

    /**
     *  设置右边的图片
     */
    fun setRightImage(rightImage: Int) {
        this.rightImage = rightImage
        this.rightImgView.setImageResource(rightImage)
    }

    /**
     * 设置右边图片显示
     */
    fun setRightImageVisible(rightImageVisible: Int) {
        this.rightImageVisible = rightImageVisible
        this.rightImgView.visibility = rightImageVisible
    }

    /********left**title**right****************image view  end****************************/
    /************************************other start*********************************************/

    /**
     * 中间的布局终极可见性
     */
    fun setTitleCenterViewVisible(visible: Int) {
        this.titleBarCenterView.visibility = visible
    }

    /**
     * 添加 title bar 中间的布局
     */
    fun addTitleCenterView(view: View, params: ViewGroup.LayoutParams = ViewGroup.LayoutParams(-2, -2)) {
        if (titleBarCenterView.childCount > 0) {
            titleBarCenterView.removeAllViews()
        }
        titleBarCenterView.visibility = View.VISIBLE
        titleBarCenterView.addView(view, params)
    }

    /**
     * 添加 title bar 中间的布局
     */
    fun addTitleCenterView(resId: Int = -1, params: ViewGroup.LayoutParams = ViewGroup.LayoutParams(-2, -2)) {
        if (resId == -1) return
        addTitleCenterView(View.inflate(context, resId, null), params)
    }
    /************************************other end*********************************************/

    /************************************event start*********************************************/
    fun setOnLeftImageClick(listener: View.OnClickListener) {
        leftImgView.setOnClickListener(listener)
    }

    fun setOnLeftTextClick(listener: View.OnClickListener) {
        leftTextView.setOnClickListener(listener)
    }

    fun setOnTitleClick(listener: View.OnClickListener) {
        titleTextView.setOnClickListener(listener)
    }

    fun setOnRightTextClick(listener: View.OnClickListener) {
        rightTextView.setOnClickListener(listener)
    }

    fun setOnRightImageClick(listener: View.OnClickListener) {
        rightImgView.setOnClickListener(listener)
    }

    /************************************event end*********************************************/

    private fun dip2px(dp: Float) = (dp * resources.displayMetrics.density + 0.5f).toInt()

    private fun Int.toDrawable() = resources.getDrawable(this)

    private fun getInternalDimensionSize(res: Resources, key: String): Int {
        var result = 0
        val resourceId = res.getIdentifier(key, "dimen", "android")
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId)
        }
        return result
    }

    /**
     * 获取标题文字

     * @return
     */
    fun getTitleText(): String {
        return titleTextView.text.toString()
    }

    fun getTitleTextView():TextView{
        return titleTextView
    }

    fun setLeftTextTag(tag: Any) {
        leftTextView.tag = tag
    }

    fun <T> getLeftTextTag(): T {
        return leftTextView.tag as T
    }

    fun setLeftImgTag(tag: Any?) {
        leftImgView.tag = tag
    }

    fun <T> getLeftImgTag(): T {
        return leftImgView.tag as T
    }

    fun setTitleTextTag(tag: Any?) {
        titleTextView.tag = tag
    }

    fun <T> getTitleTextTag(): T {
        return titleTextView.tag as T
    }

    fun setRightTextTag(tag: Any) {
        rightTextView.tag = tag
    }

    fun <T> getRightTextTag(): T {
        return rightTextView.tag as T
    }

    fun getRightTextview():TextView{
        return rightTextView
    }

    fun setRightImgTag(tag: Any) {
        rightImgView.tag = tag
    }

    fun <T> getRightImgTag(): T {
        return rightImgView.tag as T
    }

    /**
     * leo:2018-04-08 todo 获取右侧图片ImageView
     */
    fun getRightImg(): ImageView {
        return rightImgView
    }

    /**
     * leo:2018-04-08 todo 获取右侧图片ImageView
     */
    fun getLeftImg(): ImageView {
        return leftImgView
    }
}

class TitleTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : TextView(context, attrs, defStyleAttr) {
    override fun isFocused(): Boolean {
        return true
    }
}