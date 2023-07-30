INSERT INTO ms_vacancy.schedule (id, name, type, task_template, cron_expression, last_execution, deleted) VALUES
	 ('ca2c1a31-b8e1-4df8-9f67-3d261b6daf7d','hh java', 'COLLECT_HH_VACANCY', '{
    "version": "1",
    "requests": [
        {
            "queryParams": [
	            {
	                "param": "text",
	                "value": "java"
	            }
            ]
        }
    ]
}','0 * * * * ?','2000-01-01 00:00:00',false);
