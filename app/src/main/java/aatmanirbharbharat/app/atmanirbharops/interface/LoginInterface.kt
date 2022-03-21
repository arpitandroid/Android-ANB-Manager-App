package aatmanirbharbharat.app.atmanirbharops.`interface`

import aatmanirbharbharat.app.atmanirbharops.model.LoginModel
import aatmanirbharbharat.app.atmanirbharops.presenter.LoginScreenPresenter

interface LoginInterface {
    interface View {
        fun onGetDataSuccess()
        fun onGetDataFailure(message: String)
    }

    interface Presenter {
        fun getDataFromAPI( param: HashMap<String,String>)
        fun onSuccess()
        fun onFailure(message:String)
        fun showData(): LoginModel

    }

    interface Model {
        fun initRetrofitCall(param: HashMap<String,String>,presenter: LoginScreenPresenter)
        fun getData():LoginModel

    }
}