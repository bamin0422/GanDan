package h2mud2.ganpanproject.gandan.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener
import h2mud2.ganpanproject.gandan.R
import h2mud2.ganpanproject.gandan.R.*

class home_fragment: Fragment() {

    var sampleImages = arrayOf(drawable.carousel1, drawable.carousel2, drawable.carousel3, drawable.carousel4)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(layout.home_fragment, container, false)
        var carouselView = view.findViewById(R.id.item_carousel) as CarouselView

        carouselView.pageCount = sampleImages.size
        carouselView.setImageListener(imageListener)


        return view
    }

    var imageListener = ImageListener(){ i: Int, imageView: ImageView ->
        imageView.setImageResource((sampleImages[i]))
    }
}
