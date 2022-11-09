export class Session {

  isLoggedIn(): boolean {
    return sessionStorage.getItem('bearerToken') == null ? false : true;
  }

}