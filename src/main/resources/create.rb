create 'twitter', { NAME => 'user' }, { NAME => 'status' }
create 'configuration', { NAME => 'property', VERSIONS => java.lang.Integer::MAX_VALUE }

create 'tagtrend',
  { NAME => 'timeline_en', VERSIONS => java.lang.Integer::MAX_VALUE },
  { NAME => 'timeline_ja', VERSIONS => java.lang.Integer::MAX_VALUE },
  { NAME => 'timeline_es', VERSIONS => java.lang.Integer::MAX_VALUE },
  { NAME => 'timeline_de', VERSIONS => java.lang.Integer::MAX_VALUE },
  { NAME => 'timeline_fr', VERSIONS => java.lang.Integer::MAX_VALUE },
  { NAME => 'timeline_it', VERSIONS => java.lang.Integer::MAX_VALUE }
