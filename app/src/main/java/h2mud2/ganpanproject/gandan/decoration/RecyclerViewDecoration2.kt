package h2mud2.ganpanproject.gandan.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewDecoration2 : RecyclerView.ItemDecoration() {
    
    /* 2020.11.04 / 민대인
    RecyclerView의 Item들을 꾸밈
     */
    
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.left = 12
        outRect.right = 12
    }

}
