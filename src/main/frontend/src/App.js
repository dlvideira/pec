import React, { Component } from 'react'
import TodoApp from './components/todo/TodoApp'
import './App.css'
import './bootstrap.css'

class App extends Component {
    render() {
        return (
            //preciso colocar o integer dentro do {} pra transformar numa JSX expression e poder passar, nao nao compila
            // defino na hora esse by=, nao tenho ele na declaracao do metodo, mas mesmo assim consigo passar ele como PROPS
            <div className="App">
                {/*<Counter />*/}
                <TodoApp />
            </div>
        )
    }
}

export default App