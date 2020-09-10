import React, {Component} from "react";
import AuthenticationService from "./AuthenticationService.js";

class LoginComponent extends Component {

    constructor(props){
        super(props)

        this.state = {
            username: '5f3ec5079bb5ae3114c544e5',
            password: '',
            hasLoginFailed: false,
            showSuccessMessage: false
        }
        this.handleChange = this.handleChange.bind(this)
        this.loginClicked = this.loginClicked.bind(this)
    }

    handleChange(event) {
        this.setState({
            //representa username:valor
            [event.target.name]: event.target.value
        })
    }

    loginClicked(){
        if(this.state.username==='5f3ec5079bb5ae3114c544e5' && this.state.password ==='teste') {
            //aqui estou chamando o metodo de criar sess'ao no browser pelo javascript
            //da pra acessar inspecionar elemento / application / session Storage
            AuthenticationService.registerSuccessfulLogin(this.state.username, this.state.password)
            //esse e o redirect wth
            this.props.history.push(`/welcome/${this.state.username}`)
            //this.setState({showSuccessMessage: true})
        }
        else
            this.setState({hasLoginFailed: true})
    }
    render() {
        return (
            <div>
                <h1>Login</h1>
                <div className="container">
                    {/* <ShowInvalidCredentials hasLoginFailed={this.state.hasLoginFailed}/> */}
                    {/* jeito simples de fazer validacao:
                    basicamente se a priemra parte antes do && for verdadeira, retorno a segunda parte
                    desse jeito eu mato os dois metodos la em baixo que fazem essa logica*/}
                    {this.state.hasLoginFailed && <div className="alert alert-warning">Invalid Credentials</div>}

                    {/* <ShowSuccessMessage loginOk={this.state.showSuccessMessage} /> */}
                    {this.state.showSuccessMessage && <div>Login Sucessful</div>}
                    Username: <input type="text" name="username" value={this.state.username} onChange={this.handleChange}/>
                    Password: <input type="password" name="password"  value={this.state.password} onChange={this.handleChange}/>
                    <button className="btn btn-success" onClick={this.loginClicked}>Login</button>
                </div>
            </div>
        )
    }
}

export default LoginComponent