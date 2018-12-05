package subham.com.todo.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.facebook.stetho.Stetho
import dagger.android.AndroidInjection

public abstract class BaseActivity : AppCompatActivity() {
    protected lateinit var binding: ViewDataBinding
    abstract fun getLayout(): Int
    open fun isDataBindingEnabled(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        Stetho.initializeWithDefaults(this)
        if (!isDataBindingEnabled()) {
            setContentView(getLayout())
        } else {
            binding = DataBindingUtil.setContentView(this, getLayout())
        }
    }
}