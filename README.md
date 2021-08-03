## Mock Grant Disbursement API

Take home technical assignment, Q2.

A RESTful web service to manage households and people, and to search for households that qualify for grant disbursements.

---

## Endpoints

1. **Create Household**<br>`/households`<br>HTTP POST<br><br>Sample request body:<br>
   `{
   "housingType": "Landeed"
   }`
   

2. **Add family member to household** <br> `/households/{householdId}/family-members`<br>HTTP POST<br><br>Sample request body:<br>
    `{
    "name": "Bao Xian",
    "gender": "M",
    "maritalStatus": true,
    "occupationType": "Student",
    "annualIncome": 20000,
    "dob": "1997-06-12",
    "spouse": "Shi Ming"
    }
    ` <br> or <br>
   `{
   "name": "Bao Xian",
   "gender": "M",
   "maritalStatus": true,
   "occupationType": "Student",
   "annualIncome": 20000,
   "dob": "1997-06-12",
   "spouse": 2
   }`
   

3. **List households** <br> `/households`<br>HTTP GET<br>


4. **Show household** <br> `/households/{id}`<br>HTTP GET<br>


5. **Search for households and recipients of grant disbursement endpoint**<br>`/households/search?{params}`<br>HTTP GET<br><br>
    Search Parameters:
   <table>
   <tr>
   <th>Parameter</th><th>Type</th><th>Description</th>
   </tr>
   <tr>
   <td>totalIncome</td><td>int</td><td>Returns all households with total annual incomes of family members being less than the specified value</td>
   </tr>
   <tr>
   <td>childrenAge</td><td>int</td><td>Returns all households with children younger than this specified value<br><br><i>A child is considered younger than 5 years old until the day of his 5th birthday</i></td>
   </tr>
   <tr>
   <td>elderAge</td><td>int</td><td>Returns all households with elders older than this specified value<br><br><i>On the day of a person's 50th birthday, they will be considered older than 50</i></td>
   </tr>
   <tr>
   <td>hasCouple</td><td>boolean</td><td>Returns all households with a pair of spouses living in that household<br><br><i>Assumption: if a person has a spouse name but no spouse ID, the spouse is assumed to be in the same household.</i></td>
   </tr>
   </table>
   <i>Search parameters are all optional, if there are multiple parameters present, all filters will be applied</i>


6. **Delete household**<br> `/households/{id}`<br>HTTP DELETE<br>


7. **Delete family member**<br> `/households/{householdId}/family-members/{id}`<br>HTTP DELETE<br>

---

## Technical Components

**Application framework**: [Micronaut](https://micronaut.io/) <br>
**Language**: Java 11 <br>
**Database**: MySQL 8.0 on [Amazon RDS](https://aws.amazon.com/rds/) <br>
**Database Migration Plugin**: [Flyway](https://flywaydb.org/) <br>
---

## Local Setup

1. Use any MySQL 8.0 server to create a new database
2. In the created database, create a new schema
3. Set the following environment variables:

|  Environment Variable | Value  |
|---|---|
|  DB_URL |  `jdbc:mysql://{host}:{port}/{schema_name}`<br><br>*replace placeholders with host, port and schema name* |
|  DB_USERNAME |  username of database |
| DB_PASSWORD  | password of database  |
4. From the root of this repository, in the terminal, run `./gradlew run`

Voila, Flyway will handle the DB migrations and create the tables, and the app is ready to run.