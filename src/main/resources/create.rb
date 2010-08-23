create 'twitter', { NAME => 'user' }, { NAME => 'status' }
create 'configuration', { NAME => 'property', VERSIONS => java.lang.Integer::MAX_VALUE }
create 'tagtrend', { NAME => 'timeline', VERSIONS => java.lang.Integer::MAX_VALUE }, { NAME => 'hourly' }
