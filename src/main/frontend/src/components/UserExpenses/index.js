import React, {useState, useEffect} from 'react';
import axios from "axios";

// uso o props. para capturar o que estou enviando do app.js (pai)
const UserExpenses = (props) => {
    const [userExpenses, setUserExpenses] = useState([]);
    let userId = props.userId;

    // o axios [e que conversa com o backend, tem todos os rest, mas tive que configurar o cors pra poder chamar
    const GetAllUserExpenses = () => {
        axios.get(`http://localhost:8080/expenses/` + userId).then(response => {
            console.log(response);
            setUserExpenses(response.data);
        });
    };
    //TODO nao entendi essa funcao ainda
    useEffect(() => {
        GetAllUserExpenses(userId);
    }, []);
    //aqui eu retorno la pro app.js mostrar na tela. Pesquisar boas praticas se retorno o dado cru ou ja montado do jeito final, como ja em tabela, ja com style etc
    return userExpenses.map((userExpenses, index) => {
        return (
            <div key={index}>
                <h2>{userExpenses.expenseName}</h2>
                <h3>{userExpenses.category}</h3>
                <p>Gasto Total: {userExpenses.amount}</p>
                <p>Total de Parcelas: {userExpenses.totalParcels}</p>
                <p>Parcela Atual: {userExpenses.currentParcel}</p>
                <p>Frequencia de Cobrança: {userExpenses.frequency}</p>
            </div>
        );
    });
};
//sempre tem q usar esse export pra poder importar em outras classes. Esse [e o nome que fica acessivel pra todos
//TODO pesquisar se faz mais de um metodo por classe, por exemplo criar uma expense, deletar e etc tudo na mesma imitando o service do java
export default UserExpenses;
