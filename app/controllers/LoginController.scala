package controllers
import javax.inject._
import models.{LoginData, UserData}
import play.api.cache._
import play.api.data.Forms._
import play.api.data._
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{Action, _}
import services.ImpConfService

@Singleton
class LoginController @Inject()(cache: CacheApi,statusService: ImpConfService)(implicit val messagesApi: MessagesApi) extends Controller with I18nSupport  {

  val loginForm = Form(
    mapping(
      "name" -> text,
      "pass"-> text
    )(LoginData.apply)(LoginData.unapply)
  )

  def index = Action { implicit request =>

    Ok(views.html.login())
  }
  def login = Action{ implicit request=>
    loginForm.bindFromRequest.fold(
      errorForm=>{
        BadRequest(views.html.login())
      },
      validForm=> {

        val user = cache.get[UserData](validForm.name)
       user match {


          case Some(UserData(name,fname,mname,lname,age,pass,mobile,gender,hobbies,status))=> if(pass==validForm.pass)  Redirect(routes.ProfileController.index())withSession (request.session + ("mySession" -> s"${validForm.name}"))
                                                else Redirect(routes.LoginController.index()).flashing("success" -> "please enter correct password")
          case None=>  Redirect(routes.LoginController.index()).flashing("success" -> "you are not valid user")
        }
      }

    )

  }

}
