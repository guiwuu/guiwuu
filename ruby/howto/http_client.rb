require 'net/http'

Net::HTTP.start('google.com.hk', 80) do |http|
  path1 = 'http://www.taobao.com/'
  path2 = 'http://www.taobao.com/'

  # get
  response = http.get(path1)
  puts response.code

  # post
  params={"#q" => '123'}
  uri= URI.parse(path1)
  response = Net::HTTP.post_form(uri, params)
  puts response.code

  # cookie
  all_cookies = response.get_fields('set-cookie')
  cookies_array = Array.new
  all_cookies.each { |cookie|
    cookies_array.push(cookie.split('; ')[0])
  }
  cookies = cookies_array.join('; ')
  headers = {
      'Cookie' => cookies
  }

  # header
  response = http.get(path2, headers)
  puts response.code
end