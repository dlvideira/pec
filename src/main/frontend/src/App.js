import React, { Component } from 'react'
import ExpensesApp from './components/pec/ExpensesApp'
import './App.css'
import './bootstrap.css'
import './components/pec/Button.css'

class App extends Component {
    render() {
        return (
            //preciso colocar o integer dentro do {} pra transformar numa JSX expression e poder passar, nao nao compila
            // defino na hora esse by=, nao tenho ele na declaracao do metodo, mas mesmo assim consigo passar ele como PROPS
            <div className="App">
                {/*<Counter />*/}
                <ExpensesApp />
            </div>
        )
    }
}

export default App