import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.safenest.R

class DynamicLinearLayoutGenerator(private val context: Context) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun createLinearLayout(): LinearLayout {
        val parentLayout = LinearLayout(context)
        val parentParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        parentParams.setMargins(12, 60, 12, 0)
        parentLayout.layoutParams = parentParams
        parentLayout.orientation = LinearLayout.HORIZONTAL

        for (i in 0 until 3) {
            val childLayout = LinearLayout(context)
            val childParams = LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1.0f
            )
            if (i > 0) {
                childParams.setMargins(5, 0, 0, 0)
            }
            childLayout.layoutParams = childParams
            childLayout.setBackgroundColor(Color.parseColor("#FFE5E5E5"))

            val textView = TextView(context)
            val textParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            textView.layoutParams = textParams
            textView.gravity = android.view.Gravity.CENTER
            textView.text = ""
            textView.textSize = 12f
            textView.setTextColor(Color.parseColor("#FF666666"))
            textView.typeface = context.resources.getFont(R.font.lovelo)
            textView.setTypeface(null, Typeface.BOLD)
            childLayout.addView(textView)
            parentLayout.addView(childLayout)
        }
        return parentLayout
    }
}
