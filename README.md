# SlipperyMenu
手指下边缘向上滑动的menu

涉及到技术点：当我们需要addview()的view可以出现在屏幕外边，还有接收action-outside时需要添加flag:
mMenuParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH|
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
 当添加的view,在上面出发down事件之后，后面的移动时候，move up事件消费都还是在当前添加的View上面，所以借用此规律，可以做view随着手指上下移动的效果。

