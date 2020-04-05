package ar.com.wolox.wolmo.core.qr

import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.journeyapps.barcodescanner.ViewfinderView

internal interface ScanQRView {
    
    fun decoratedBarcodeView(): DecoratedBarcodeView

    fun onResultCanceled()

    fun onResultReceived(result: String)
}
