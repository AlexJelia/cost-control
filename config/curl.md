### curl samples (application deployed in application context `costs_control`).
> For windows use `Git Bash`

#### get All Users
`curl -s http://localhost:8080/costs_control/rest/admin/users --user admin@gmail.com:admin`

#### get Users 100001
`curl -s http://localhost:8080/costs_control/rest/admin/users/100001 --user admin@gmail.com:admin`

#### get All Costs
`curl -s http://localhost:8080/costs_control/rest/profile/costs --user user@yandex.ru:password`

#### get Costs 100003
`curl -s http://localhost:8080/costs_control/rest/profile/costs/100003 --user user@yandex.ru:password`

#### filter Costs   
`curl -s "http://localhost:8080/costs_control/rest/profile/costs/filter?startDate=2022-05-30&startTime=07:00:00&endDate=2022-05-31&endTime=11:00:00" --user user@yandex.ru:password`

#### get Costs not found
`curl -s -v http://localhost:8080/costs_control/rest/profile/costs/100008 --user user@yandex.ru:password`

#### delete Costs
`curl -s -X DELETE http://localhost:8080/costs_control/rest/profile/costs/100002 --user user@yandex.ru:password`

#### create Costs
`curl -s -X POST -d '{"dateTime":"2022-06-01T12:00","description":"Created lunch","cost":300}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/costs_control/rest/profile/costs --user user@yandex.ru:password`

#### update Costs
`curl -s -X PUT -d '{"dateTime":"2022-05-30T07:00", "description":"Updated breakfast", "cost":200}' -H 'Content-Type: application/json' http://localhost:8080/costs_control/rest/profile/costs/100003 --user user@yandex.ru:password`

### validate with Error
`curl -s -X POST -d '{}' -H 'Content-Type: application/json' http://localhost:8080/costs_control/rest/admin/users --user admin@gmail.com:admin`