package ar.com.wolox.wolmo.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import ar.com.wolox.wolmo.qr.R
import com.journeyapps.barcodescanner.DecoratedBarcodeView

class QRLayout : DecoratedBarcodeView {

    constructor(context: Context) : super(context) { init() }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) { init() }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) { init() }

    private fun init() {
        View.inflate(context, R.layout.activity_custom_scanner, this)
    }

}
