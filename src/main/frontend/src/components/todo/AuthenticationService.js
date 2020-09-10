class AuthenticationService {
    registerSuccessfulLogin(userId, password){
        sessionStorage.setItem('userId', userId)
    }

    logout(){
        sessionStorage.removeItem('userId')
    }

    isUserLoggedIn(){
        let user = sessionStorage.getItem('userId')
        return user !== null;
    }

    getLoggedUserId() {
        let user = sessionStorage.getItem('userId')
        if(user===null) return ''
        return user
    }
}

export default new AuthenticationService()