import React, {Component} from "react";
import {Field, Form, Formik} from "formik";
import ExpensesService from "../../api/todo/ExpensesService.js";


class TodoComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            expenseId: this.props.match.params.expenseId,
            expenseName: "",
            amount: 0,
            category: "",
            totalParcels: "",
            currentParcel: 0,
            frequency: 0
        }
        this.onSubmit = this.onSubmit.bind(this)
    }

    onSubmit(values) {
        let expenseToUpdate = {
            expenseName: values.expenseName,
            amount: values.amount,
            category: values.category,
            totalParcels: values.totalParcels,
            currentParcel: values.currentParcel,
            frequency: values.frequency
        }
        if (this.state.expenseId === "0") {
            ExpensesService.createExpense(expenseToUpdate)
                .then(() => this.props.history.push('/todos'))
        } else {
            expenseToUpdate.expenseId = this.state.expenseId
            ExpensesService.updateExpense(expenseToUpdate)
                .then(() => this.props.history.push('/todos'))
        }
    }

    componentDidMount() {
        if (this.state.expenseId === "0") {
            return
        }
        this.refreshExpense();
    }

    refreshExpense() {
        ExpensesService.getExpense(this.state.expenseId)
            .then(
                response => {
                    this.setState({
                        expenseId: response.data.expenseId,
                        expenseName: response.data.expenseName,
                        amount: response.data.amount,
                        category: response.data.category,
                        totalParcels: response.data.totalParcels,
                        currentParcel: response.data.currentParcel,
                        frequency: response.data.frequency
                    })
                }
            )

    }

    render() {
        let {expenseId, expenseName, amount, category, totalParcels, currentParcel, frequency} = this.state
        return (
            <div>
                <h1>Todo</h1>
                <div className="container">
                    <Formik
                        initialValues={{
                            expenseId,
                            expenseName,
                            amount,
                            category,
                            totalParcels,
                            currentParcel,
                            frequency
                        }}
                        enableReinitialize={true}
                        onSubmit={this.onSubmit}>
                        {
                            (props) => (
                                <Form>
                                    <fieldset className="form-group">
                                        <label>Nome / Descrição</label>
                                        <Field className="form-control" type="text" name="expenseName"/>
                                    </fieldset>
                                    <fieldset className="form-group">
                                        <label>Valor</label>
                                        <Field className="form-control" type="number" name="amount"/>
                                    </fieldset>
                                    <fieldset className="form-group">
                                        <label>Categoria</label>
                                        <Field className="form-control" type="text" name="category"/>
                                    </fieldset>
                                    <fieldset className="form-group">
                                        <label>Total de Parcelas</label>
                                        <Field className="form-control" type="number" name="totalParcels"/>
                                    </fieldset>
                                    <fieldset className="form-group">
                                        <label>Parcela Atual</label>
                                        <Field className="form-control" type="number" name="currentParcel"/>
                                    </fieldset>
                                    <fieldset className="form-group">
                                        <label>Frequência (dias)</label>
                                        <Field className="form-control" type="text" name="frequency"/>
                                    </fieldset>
                                    {this.state.expenseId === "0" ?
                                        <button className="btn btn-success" type="submit">Criar</button>
                                        :
                                        <button className="btn btn-success" type="submit">Salvar</button>
                                    }
                                </Form>
                            )
                        }
                    </Formik>
                </div>
            </div>
        )
    }
}

export default TodoComponent