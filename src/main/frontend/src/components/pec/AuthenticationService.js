import axios from "axios";

class AuthenticationService {

    executeBasicAuthenticationService(username, password) {
        return axios.get(`http://localhost:8080/basicauth/${username}`,
            {headers: {authorization: this.createBasicAuthToken(username, password)}})
    }

    registerSuccessfulLogin(username, password, userId) {
        sessionStorage.setItem('userId', userId)
        this.setupAxiosInterceptors(this.createBasicAuthToken(username, password))
    }

    logout() {
        sessionStorage.removeItem('userId')
    }

    isUserLoggedIn() {
        let user = sessionStorage.getItem('userId')
        return user !== null;
    }

    getLoggedUserId() {
        let user = sessionStorage.getItem('userId')
        if (user === null) return ''
        return user
    }

    createBasicAuthToken(username, password) {
        return 'Basic ' + window.btoa(username + ":" + password)
    }

    setupAxiosInterceptors(basicAuthHeader) {
        axios.interceptors.request.use(
            (config) => {
                if (this.isUserLoggedIn()) {
                    config.headers.authorization = basicAuthHeader
                }
                return config
            }
        )
    }
}

export default new AuthenticationService()