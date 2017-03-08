package controllers
import javax.inject._
import models.UserData
import play.api.cache.CacheApi
import play.api.mvc._
import services.ImpConfService

@Singleton
class DeleteControler @Inject()(cache: CacheApi)(statusService: ImpConfService) extends Controller {


  def delete(id:String) = Action { implicit  request =>
    println("--------------------")
    val u = statusService.userdata
    u.foreach(println)
    cache.remove(id)
    //statusService.userdata-= id
    //statusService.userdata.foreach(println)

   // println(">>>>>>>>>>>>>>."+statusService.userdata.toString)
    //Ok(">>>>>>>>>"+cache.get[UserData](id))
    Ok("Data Deleted SuccessFully")
  }

  def view(id:String) = Action{ implicit request =>
    val user = cache.get[UserData](id)
    user match {
      case Some(UserData(uname,fname,mname,lname,age,pass,mobile,gender,hobbies,status)) =>  Ok(views.html.profile(uname,fname,mname,lname,age,mobile,gender,hobbies))
      case None => Ok("sorry user data has been suspended") //Redirect(routes.LoginController.index()).flashing("success" -> "login first")
    }


  }

}
