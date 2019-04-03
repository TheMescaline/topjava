#### Examples of cURL requests for meals REST API

Get list of all meals:

    curl -X GET \
     http://localhost:8080/topjava/rest/profile/meals
  
Get list of meals filtered by dates and time:
  
    curl -X GET \
    'http://localhost:8080/topjava/rest/profile/meals/between?startDate=2015-05-31&startTime=12:00&endDate=2015-05-31&endTime=19:00'
    
Get one specific meal:

    curl -X GET \
     http://localhost:8080/topjava/rest/profile/meals/100007
     
Create new meal:

    curl -X POST \
      http://localhost:8080/topjava/rest/profile/meals \
      -H 'Content-Type: application/json' \
      -d '{
        "dateTime": "2019-03-31T15:00:00",
        "description": "New dinner",
        "calories": 1200
      }'
      
Update existing meal:

    curl -X PUT \
      http://localhost:8080/topjava/rest/profile/meals/100010 \
      -H 'Content-Type: application/json' \
      -d '{
    	"dateTime": "2001-01-01T01:00:00",
        "description": "Updated dinner",
            "calories": 1800
    }'
    
Delete existing meal

    curl -X DELETE \
      http://localhost:8080/topjava/rest/profile/meals/100010