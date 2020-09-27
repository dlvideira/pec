import React, { Component } from 'react'
import {BrowserRouter as Router,  Route, Switch} from 'react-router-dom'
import AuthenticatedRoute from './AuthenticatedRoute'
import LoginComponent from "./LoginComponent"
import ListExpensesComponent from "./ListExpensesComponent"
import HeaderComponent from "./HeaderComponent"
import ErrorComponent from "./ErrorComponent"
import FooterComponent from "./FooterComponent"
import LogoutComponent from "./LogoutComponent"
import WelcomeComponent from "./WelcomeComponent"
import ExpenseComponent from "./ExpenseComponent"

class ExpensesApp extends Component{
    render() {
        //esse Rouuter funciona com um controller, cara requisicao nessa url encaminha para o component especifico
        return (
            <div className="TodoApp">
                <Router>
                    <HeaderComponent />
                    {/* esse switch faz que so valha um path, senao o error ele fica se repetindo */}
                    <Switch>
                        <Route path="/" exact component={LoginComponent}/>
                        <Route path="/login" component={LoginComponent}/>
                        <AuthenticatedRoute path="/welcome/:name" component={WelcomeComponent}/>
                        <AuthenticatedRoute path="/todo/:expenseId" component={ExpenseComponent}/>
                        <AuthenticatedRoute path="/todos" component={ListExpensesComponent}/>
                        <AuthenticatedRoute path="/logout" component={LogoutComponent}/>
                        {/*esse ultimo só é chamado caso nao encontre nenhum Route antes (é como um fallback), personalizar a página pra deixar friendly*/}
                        <Route component={ErrorComponent}/>
                    </Switch>
                    <FooterComponent />
                </Router>
                {/* <LoginComponent />
                <WelcomeComponent /> */}
            </div>
        )
    }
}

export default ExpensesApp