package ar.com.wolox.wolmo.core.qr

import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.RequiresApi
import ar.com.wolox.wolmo.core.fragment.WolmoFragment
import ar.com.wolox.wolmo.core.presenter.BasePresenter
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.journeyapps.barcodescanner.ViewfinderView
import kotlinx.android.synthetic.main.custom_barcode_scanner.*

abstract class ScanQRFragment<T : BasePresenter<*>> : WolmoFragment<T>(), ScanQRView, DecoratedBarcodeView.TorchListener {

    private lateinit var scanQRView: ScanQRView

    private var capture: CaptureManager? = null
    private var barcodeScannerView: DecoratedBarcodeView? = null

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scanQRView = this
        IntentIntegrator(requireActivity()).run {
            /*
                ADD ANY EXTRA THAT YOU WANT TO PASS TO CAPTURE QR ACTIVITY HERE
             */
            setDesiredBarcodeFormats(IntentIntegrator.QR_CODE) // --> DEFINE THE FORMAT OF THE CODE YOUR SCANNING
            captureActivity = requireActivity()::class.java
            initiateScan()
        }
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        barcodeScannerView = scanQRView.decoratedBarcodeView().apply { setTorchListener(this@ScanQRFragment) }
        capture = CaptureManager(requireActivity(), barcodeScannerView!!).apply {
            initializeFromIntent(requireActivity().intent, savedInstanceState)
            decode()
        }
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        capture?.onResume()
    }

    @CallSuper
    override fun onPause() {
        super.onPause()
        capture?.onPause()
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        capture?.onDestroy()
    }

    @CallSuper
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        capture?.onSaveInstanceState(outState)
    }

    protected fun onViewfinderViewVisibility(status: Int) {
        zxing_viewfinder_view.visibility = status
    }

    override fun onTorchOn() { /*ON FLASH ON*/ }

    override fun onTorchOff() { /*ON FLASH OFF*/ }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_CANCELED -> {
                scanQRView.onResultCanceled()
            }
            else -> {
                IntentIntegrator.parseActivityResult(requestCode, resultCode, data)?.let {
                    it.contents?.let { resultContent ->
                        scanQRView.onResultReceived(resultContent)
                    }
                }
            }
        }
    }
}

