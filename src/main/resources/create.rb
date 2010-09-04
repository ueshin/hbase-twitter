create 'twitter', { NAME => 'user' }, { NAME => 'status' }
create 'configuration', { NAME => 'property', VERSIONS => java.lang.Integer::MAX_VALUE }

create 'tagtrend',
  { NAME => 'timeline_en', VERSIONS => java.lang.Integer::MAX_VALUE }, { NAME => 'score_en' },
  { NAME => 'timeline_ja', VERSIONS => java.lang.Integer::MAX_VALUE }, { NAME => 'score_ja' },
  { NAME => 'timeline_es', VERSIONS => java.lang.Integer::MAX_VALUE }, { NAME => 'score_es' },
  { NAME => 'timeline_de', VERSIONS => java.lang.Integer::MAX_VALUE }, { NAME => 'score_de' },
  { NAME => 'timeline_fr', VERSIONS => java.lang.Integer::MAX_VALUE }, { NAME => 'score_fr' },
  { NAME => 'timeline_it', VERSIONS => java.lang.Integer::MAX_VALUE }, { NAME => 'score_it' }
