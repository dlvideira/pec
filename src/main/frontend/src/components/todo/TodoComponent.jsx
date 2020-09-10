import React, {Component} from "react";
import {Field, Form, Formik} from "formik";


class TodoComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            expenseId : this.props.match.params.id,
            expenseName : "hardCoded",
            amount : 0,
            category : "hardCoded",
            totalParcels : 0,
            currentParcel : 0,
            frequency : "fazer Lista Suspensa",
        }
        this.onSubmit = this.onSubmit.bind(this)
    }

    onSubmit(values){
        console.log(values)
    }

    render() {
        let {expenseName, amount, category, totalParcels, currentParcel, frequency} = this.state
        return (
            <div>
                <h1>Todo</h1>
                    <div className="container">
                        <Formik
                            initialValues={{expenseName, amount, category, totalParcels, currentParcel, frequency}}
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
                                            <label>Frequência</label>
                                            <Field className="form-control" type="text" name="frequency"/>
                                        </fieldset>
                                        <button className="btn btn-success" type="submit">Salvar</button>
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