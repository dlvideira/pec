import React, {Component} from "react";
import AuthenticationService from "./AuthenticationService";
import { withRouter } from 'react-router';
import {Link} from "react-router-dom";

class HeaderComponent extends Component{
    render() {
        const isUserLoggedIn = AuthenticationService.isUserLoggedIn();

        return (
            <header>
                <nav className="navbar navbar-expand-md navbar-dark bg-dark">
                    <div><a href="/" className="navbar-brand">Link para o site</a></div>
                    <ul className="navbar-nav">
                        {isUserLoggedIn && <li><Link className="nav-link" to="/welcome/userTeste">Home</Link></li>}
                        {isUserLoggedIn && <li><Link className="nav-link" to="/todos">Todos</Link></li>}
                    </ul>
                    <ul className="navbar-nav navbar-collapse justify-content-end">
                        {!isUserLoggedIn && <li><Link className="nav-link" to="/login">Login</Link></li>}
                        {/* aqui eu nao uso logou() pq estaria chamando o metodo, o que preciso e mapear um evento, por isso nao uso o () */}
                        {isUserLoggedIn && <li><Link className="nav-link" to="/logout"
                                                     onClick={AuthenticationService.logout}>Logout</Link></li>}
                    </ul>
                </nav>
            </header>
        )
    }
}

export default withRouter(HeaderComponent)