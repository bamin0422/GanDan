package h2mud2.ganpanproject.gandan.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener
import h2mud2.ganpanproject.gandan.R
import h2mud2.ganpanproject.gandan.R.*
import h2mud2.ganpanproject.gandan.activity.item.BannerActivity
import h2mud2.ganpanproject.gandan.activity.item.DesignItemActivity
import h2mud2.ganpanproject.gandan.activity.item.HangingActivity
import h2mud2.ganpanproject.gandan.activity.item.SteelBannerActivity

class home_fragment: Fragment() {

    var sampleImages = arrayOf(drawable.carousel1, drawable.carousel2, drawable.carousel3, drawable.carousel4)
    lateinit var bannerBtn : Button
    lateinit var steelBannerBtn : Button
    lateinit var hangingBtn : Button
    lateinit var designItemBtn : Button



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(layout.home_fragment, container, false)
        var carouselView = view.findViewById(R.id.item_carousel) as CarouselView

        carouselView.pageCount = sampleImages.size
        carouselView.setImageListener(imageListener)

        bannerBtn = view.findViewById(R.id.bannerButton)
        steelBannerBtn = view.findViewById(R.id.steelBannerButton)
        hangingBtn = view.findViewById(R.id.hangingButton)
        designItemBtn = view.findViewById(R.id.designItemButton)

        bannerBtn.setOnClickListener {
            activity?.let{
                var intent = Intent(view.context, BannerActivity::class.java)
                startActivity(intent)
            }
        }

        steelBannerBtn.setOnClickListener {
            activity?.let{
                var intent = Intent(view.context, SteelBannerActivity::class.java)
                startActivity(intent)
            }
        }

        hangingBtn.setOnClickListener {
            activity?.let{
                var intent = Intent(view.context, HangingActivity::class.java)
                startActivity(intent)
            }
        }

        designItemBtn.setOnClickListener {
            activity?.let{
                var intent = Intent(view.context, DesignItemActivity::class.java)
                startActivity(intent)
            }
        }

        return view
    }

    var imageListener = ImageListener(){ i: Int, imageView: ImageView ->
        imageView.setImageResource((sampleImages[i]))
    }
}
