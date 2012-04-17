# Expression/Language

## Category 

Categories are used to categorize classes!
This categorization allows, for example, to indicate if:

* a class is an `entity`, a `value_object`, a `dao` (Data Access Object)...
* a specific component of a design pattern such as a `view` or a `controller` ...
* a member of a specific "technical" layer such as `domain`, `infrastructure`, `presentation`...
* or a member of a specific "business" layer such as `billing`, `planning`, ...
* ...

Category allows to define specific selector in the Patternity bounded context. 
Each class can belongs to zero, one or more categories. 

### Annotation

A category can be define by annotation, that is a class belongs to the category if it has the corresponding annotation at its type level.

```json
categories {
	"VO" :     "@com.patternity.annotation.ValueObject",
    "Entity" : "@com.patternity.annotation.Entity",
   	"ddd" :    "@(com.patternity.annotation.*)"
}
```

### Member of a package

```json
categories {
	"VO" :  "com.mybusiness.domain.valueobject.*",
    "Entity" : "com.mybusiness.domain.entity..*"
}
```

All classes that belongs to `com.mybusiness.domain.valueobject` package are categorized as `ValueObject`, whereas classesthat belongs to `com.mybusiness.domain.valueobject.mapper` are not.

All classes that belongs to `com.mybusiness.domain.entity` package *and* sub-packages are categorized as `Entity`.

### Name pattern

```json
categories {
	"VO" :  "*VO",
    "Entity" : "*Entity"
}
```

### Qualified name

```json
categories {
	"VO" :  "com.mybusiness.domain..*.*VO",
    "Entity" : "com.mybusiness.domain..*.*Entity"
}
```

### Method signature

`#<method_name> ( <comma_separated_argument_type> )`


```json
  "Saveable": "#save(java.sql.Connection)" 
```

### Composition of selector

* `and`
* `or`

```json
  "Service": "@com.patternity.annotation.Service or @com.springframework.stereotype.Service or *Service"
  "DAO": "@com.patternity.annotation.DAO or *Database",
  "Persistent": ""
```

According to the following variables:

```
  def package = com.mybusiness.domain
  def entity  = $package..*.*Entity
  def aroot   = $package..*.*AggregateRoot
```

All the following selectors are equivalents:

* `com.mybusiness.domain..*.*Entity or com.mybusiness.domain..*.*AggregateRoot`
* `com.mybusiness.domain..*.{*Entity or *AggregateRoot}`
* `$package..*.{*Entity or *AggregateRoot}`
* `$entity or $aroot`

# Rules

A rule is generally based on one or more selector. Selector can be predefined in variable for reusability or for a better readability, but can also be directly inlined in the rule.

e.g. with `VO = *ValueObject`

the rule: `$VO-x->Entity` and `*ValueObject-x->Entity` are equivalents.

## Rule [VO-x->Entity] 

A ValueObject cannot depends on an Entity.

Syntax: `[selector1]-x->[selector2]`

* classes that belongs to `selector1` selector cannot depend on classes that belongs to `selector2`.

## Rule [VO has #{equals(java.lang.Object),hashcode()}]

A ValueObject must implements `equals` and `hashcode` methods

Syntax: `[selector]#{methods}`

* classes that belongs to `selector` must (implements or directly override) the methods defined in `methods`
* `methods` is a comma separated list of method signature (without its return type). 

see Method signature.

## Rule [VO has #*{final *}]

A ValueObject must have all its fields final

`$VO satisfy #*.{static or final}`


