import React, {Component} from "react";
import ExpensesService from "../../api/todo/ExpensesService.js";
import {BSON,Long, ObjectID,Binary,Code,DBRef,Symbol,Double,Timestamp,MaxKey,MinKey} from 'bson'


class ListTodosComponent extends Component {
    constructor(props){
        super(props)
        this.state = {
            actionMessage : null,
            expenses: []
        }
        this.refreshExpenses = this.refreshExpenses.bind(this)
        this.deleteExpense = this.deleteExpense.bind(this)
    }

    componentDidMount() {
        this.refreshExpenses();
    }

    refreshExpenses() {
        ExpensesService.getAllExpenses("5f3ec5079bb5ae3114c544e5")
            .then(
                response => {
                    //console.log(response)
                    this.setState({expenses : response.data})
                }
            )
    }

    deleteExpense(expenseId) {
        ExpensesService.deleteExpense(expenseId)
            .then(
                response => {
                    this.setState({message: response.data.body.message})
                    this.refreshExpenses()
                }
            )
    }

    render(){
        return(
            <div>
                <h1>List Expenses</h1>
                <div className="container">
                    <table className="table">
                        <thead>
                        <tr>
                            <th>Descrição</th>
                            <th>Total</th>
                            <th>Categoria</th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            this.state.expenses.    map(
                                expense =>
                                    // ele nao le o expenseId direito, da erro / se uso toString ele printa object Object, a key tem q ser o expense id
                                    <tr key={expense.expenseName}>
                                        <td>{expense.expenseName}</td>
                                        <td>{expense.amount}</td>
                                        <td>{expense.category}</td>
                                        <td><button className="btn btn-warning" onClick={() => getHexa(expense.expenseId)}>testarID</button></td>
                                        <td><button className="btn btn-warning" onClick={() => this.deleteExpense(expense.expenseId)}>BKP</button></td>
                                    </tr>
                            )
                        }
                        </tbody>
                    </table>
                </div>
            </div>)
    }
}

function getHexa(object){
    const bson = require('bson')
    let tooo = bson.ObjectId
    // console.log("1: " + bson)
    // const teste = new ObjectID()
    // console.log("2: " + teste)
    //
    // const shhhh = bson.toHexString(object)
    // console.log("4: " + shhhh )
    console.log(tooo)
    return null
}

export default ListTodosComponent