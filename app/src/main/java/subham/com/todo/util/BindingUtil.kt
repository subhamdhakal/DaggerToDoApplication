package subham.com.todo.util

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.support.v7.content.res.AppCompatResources
import android.widget.ImageView

@BindingAdapter("app:srcCompat")
fun bindSrcCompat(imageView: ImageView, drawable: Int) {
    if (drawable == 0) {
        imageView.setImageDrawable(null)
        return
    }
    var image: Drawable = AppCompatResources.getDrawable(imageView.context, drawable)!!
    imageView.setImageDrawable(image)
}