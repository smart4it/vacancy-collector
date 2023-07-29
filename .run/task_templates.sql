INSERT INTO ms_vacancy.task_template (id,title,type, specification,cron_expression,last_execution,deleted) VALUES
	 ('ca2c1a31-b8e1-4df8-9f67-3d261b6daf7d','hh java', 'hh_vacancies', '{
    "version": "1",
    "id": "",
    "timestamp": "",
    "title": "",
    "requests": [
        {
            "method": "GET",
            "url": "https://api.hh.ru/vacancies",
            "queryParams": [
	            {
	                "param": "text",
	                "value": "java"
	            }
            ],
            "body": ""
        }
    ],
    "pagination": {
        "total": 2000,
        "pageSize": 100
    }
}','0 * * * * ?','2000-01-01 00:00:00',false);
