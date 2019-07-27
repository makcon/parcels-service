# Parcels management service

### Technology Requirements

- Choose the language you feel most confortable with
- Use of a framework
- Unit tests & Integration tests
- The application has to be self-contained. That is, should not depend on extenernal connections not provided with it.

### Storage
Use in memory storage. Do not depend on an installed database.

## The task at hand
Create an application listening to port 8085, and:

### 1
The application should be able to receive a
POST `http://localhost:8085/api/register` with payload (store it as a `shipment`):

```javascript
{
  "reference":"ABCD123456",
  "parcels" : [
  {
    "weight":1,
    "width": 10,
    "height": 10,
    "length": 10
  },
  {
    "weight":2,
    "width": 20,
    "height": 20,
    "length": 20
  }
  ]
}
```


### 2

The application should be able to receive a
PUT `http://localhost:8085/api/push` with the following payloads (`tracking`):

#### A

```javascript
{
  "status":"WAITING_IN_HUB",
  "parcels":2,
  "weight":null,
  "reference":"ABCD123456"
}
```

#### B

```javascript
{
  "status":"WAITING_IN_HUB",
  "parcels":2,
  "weight":2,
  "reference":"ABCD123456"
}
```

#### C

```javascript
{
  "status":"WAITING_IN_HUB",
  "parcels":1,
  "weight":15,
  "reference":"ABCD123456"
}
```

#### D

```javascript
{
  "status":"WAITING_IN_HUB",
  "parcels":2,
  "weight":30,
  "reference":"ABCD123456"
}
```

#### E

```javascript
{
  "status":"DELIVERED",
  "parcels":2,
  "weight":2,
  "reference":"ABCD123456"
}
```

#### F

```javascript
{
  "status":"DELIVERED",
  "parcels":2,
  "weight":30,
  "reference":"ABCD123456"
}
```

#### G

```javascript
{
  "status":"DELIVERED",
  "parcels":2,
  "weight":30,
  "reference":"EFGH123456"
}
```

#### H

```javascript
{
  "status":"DELIVERED",
  "parcels":null,
  "weight":30,
  "reference":"ABCD123456"
}
```

#### Business logic
Using the above examples:

-----

Given the provided `shipment` 
When
- `shipment` reference should be equal to `tracking` reference 
- `shipment` parcel number should be equal to `tracking` parcel number.
- `shipment` total weight should be less than `tracking` weight.
- `tracking` status should be `DELIVERED`

Then dispatch an application event

```javascript
{
  "reference":"ABCD123456",
  "status": "CONCILLIATION_REQUEST"
}
```
AND print it into the console

- - - - - 

Given the provided `shipment` 
When
- `shipment` reference should be equal to `tracking` reference. 
- `shipment` parcel number should be equal to `tracking` parcel number.
- `shipment` total weight should be **greater or equal** than `tracking` weight.
- `tracking` status should be `DELIVERED`.

Then dispatch an application event

```javascript
{
  "reference":"ABCD123456",
  "status": "NOT_NEEDED"
}
```
AND print it into the console

- - - - - 

Given the provided `shipment` 
When

- `shipment` reference should be equal to `tracking` reference 
- `tracking` status is not `DELIVERED`

Then dispatch an application event

```javascript
{
  "reference":"ABCD123456",
  "status": "INCOMPLETE"
}
```
AND print it into the console

- - - - - 
Given the provided `shipment` 
When

- `shipment` reference should be equal to `tracking` reference 
- any other `tracking` field is null

Then dispatch an application event

```javascript
{
  "reference":"ABCD123456",
  "status": "INCOMPLETE"
}
```
AND print it into the console
- - - - - 
Given the provided `shipment` 
When
- `tracking` reference is not equal to`shipment` reference 

Then dispatch an application event

```javascript
{
  "reference":"EFGH123456",
  "status": "NOT_FOUND"
}
```
AND print it into the console

- - - - - 

