function isrussian (s)
  i = utf8.offset(s, 1)
  j = utf8.offset(s, 2) - 1
  uchar = string.sub(s, i, j)
  return uchar >= 'А' and uchar <= 'я'
end

function isphonetic (s)
  return string.sub(s, 1, 1) == '/'
end

function Strong (elem)
  return pandoc.SmallCaps(elem.content)
end

function Str (elem)
  if isrussian(elem.text) then
    return pandoc.RawInline('latex', '\\RussianWord{' .. elem.text .. '}')
  elseif isphonetic(elem.text) then
    return pandoc.RawInline('latex', '{\\phonetic ' .. elem.text .. '}')
  else
    return pandoc.Str(elem.text)
  end
end
