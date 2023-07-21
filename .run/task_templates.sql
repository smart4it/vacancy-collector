INSERT INTO task_template (id,title,specification,cron_expression,last_execution,deleted) VALUES
	 ('ca2c1a31-b8e1-4df8-9f67-3d261b6daf7d','hh java','{
    "version": "1",
    "id": "",
    "timestamp": "",
    "title": "",
    "protocol": "http",
    "type": "HH_VACANCY",
    "requests": [
        {
            "method": "GET",
            "url": "https://api.hh.ru/vacancies",
            "queryParams": [
	            {
	                "param": "text",
	                "value": "java",
	            }
            ],
            "body": ""
        }
    ],
    "pagination": {
        "total": 2000,
        "pageSize": 100
    }
}','* */5 * * * ?','2000-01-01 00:00:00',false);
