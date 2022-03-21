package aatmanirbharbharat.app.atmanirbharops.presenter

import aatmanirbharbharat.app.atmanirbharops.`interface`.LoginInterface
import aatmanirbharbharat.app.atmanirbharops.model.LoginDataCall

class LoginScreenPresenter(_view: LoginInterface.View) : LoginInterface.Presenter {

    private var view: LoginInterface.View = _view
    private var APICall: LoginDataCall = LoginDataCall()

    override fun onSuccess() {
        view.onGetDataSuccess()
    }

    override fun onFailure(message: String) {
        view.onGetDataFailure(message)
    }

    override fun showData()= APICall.getData()


    override fun getDataFromAPI(param: HashMap<String,String>) {
        APICall.initRetrofitCall(param, this)

    }


}