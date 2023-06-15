# power-up-traceability

The traceability microservice is a service which creates, stores and manages information for later use, from the service of plazoleta, when making status changes in different orders, automatically communicates with this microservice creating different logs of the orders so that this communication is implicit through the actions of the microservice of plazoleta.

/traceability/logs

Endpoint that is automated with the services of the terminal.

When an employee takes the actions of an order, these can be reflected in information that the customer can get to know, in this way the customer can track his order and has access to this information, has access to the history of the logs of his orders.

ENDPOINT: /traceability/logs/record/

The system does not ask for any information, simply by being logged in as a customer in this endpoint the system is responsible for identifying the user and return the information, you can only see the information of the customer logged in.
And to make use of the richness of the data that the application allows us, we have a specific record of the orders and the performance of employees, allowing to know the time of an order between its start and end and know the performance of employees by obtaining the record of which employees have participated in which orders and the average time between the start and end of the order.

ENDPOINT: /traceability/logs/time/{idOrder}
```JSON
"RETURNS RECORD OF THE ORDER TIME BETWEEN START AND END OF ORDER ACCORDING TO THE ORDER ID"
```
ENDPOINT: /traceability/logs/time/employee/ranked/{idEmployee}
```JSON
"RETURNS RANKING OF TIMES AN EMPLOYEE CHANGES ORDER STATUS"
```
