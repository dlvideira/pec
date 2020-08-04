import React from 'react';
import UserExpenses from './components/UserExpenses'
import './App.css';

function App() {
  return (
      //essa chamada de "metodo" passando parametro eu capturo do outro lado (chamado  Child) como props (props.userId)
    <div className="App">
        <UserExpenses userId = "5f289d05436f590e8c53d783"/>
    </div>
  );
}

export default App;
