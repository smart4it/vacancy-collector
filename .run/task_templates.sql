INSERT INTO ms_vacancy.task_template (id,title,specification,cron_expression,last_execution,deleted) VALUES
	 ('ca2c1a31-b8e1-4df8-9f67-3d261b6daf7d','hh java','{
    "version": "1",
    "id": "",
    "timestamp": "",
    "title": "",
    "protocol": "http",
    "type": "HH",
    "requests": [
        {
            "method": "GET",
            "url": "https://api.hh.ru/vacancies",
            "queryParams": [
                "text=java"
            ],
            "body": ""
        }
    ],
    "pagination": {
        "total": 2000,
        "pageSize": 100
    }
}','* */5 * * * ?','2023-07-15 23:15:00',false);
