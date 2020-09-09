import React, {Component} from "react";
import ExpensesService from "../../api/todo/ExpensesService.js";

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
                    this.setState({expenses : response.data})
                }
            )
    }

    deleteExpense(expenseId) {
        ExpensesService.deleteExpense(expenseId)
            .then(
                response => {
                    this.setState({actionMessage: response.data})
                    this.refreshExpenses()
                }
            )
    }

    render(){
        return(
            <div>
                <h1>List Expenses</h1>
                {this.state.actionMessage && <div className="alert alert-success">{this.state.actionMessage}</div>}
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
                                        <td><button className="btn btn-danger btn-circle" onClick={() => this.deleteExpense(expense.expenseId)}>X</button></td>
                                    </tr>
                            )
                        }
                        </tbody>
                    </table>
                </div>
            </div>)
    }
}

export default ListTodosComponent