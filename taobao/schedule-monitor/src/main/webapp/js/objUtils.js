/*	将传入对象转换为json字符串,无法转换function.
 *	@param: 
 *		obj(任意类型) 目标对象.
 *	@return: (string) 与obj对应的json字符串.
 */
function obj2json(obj)
{
	switch(typeof(obj))
	{
		case 'object':
			if(!obj)
				return null;
			var str = new String();
			if(typeof(obj.length) == 'number')//有序
			{
				str = '[';
				for(i in obj)
					str += obj2json(obj[i]) + ',';
				if(str.charAt(str.length-1) == ',')
					str = str.substring(0,str.length-1) + ']';
				else
					str += ']';
			}
			else//无序
			{
				str = '{';
				for(i in obj)
					str += i + ':' + obj2json(obj[i]) + ',';
				if(str.charAt(str.length-1) == ',')
					str = str.substring(0,str.length-1) + '}';
				else
					str += '}';
			}
			return str;
		case 'string':
			obj = obj.replace(/[\s\\\'\,]/g,"");
			return '\'' + obj + '\'';
		case 'number':
			return obj;
		case 'boolean':
			return obj;
		case 'undefined':
			alert('error:\n ' + obj + 'is undefined.\n 无法将它转换成json.');
			return '';
		case 'function':
			alert('error:\n ' + obj + 'is function.\n 无法将它转换成json.');
			return '';
		default:
			alert('error:\n ' + obj + ' 未知.\n 无法将它转换成json.');
			return '';
	}
}
/*	将传入对象完全拷贝并返回拷贝对象的引用
 *	@param: 
 *		obj(任意类型) 目标对象.
 *	@return: (任意类型) 与obj相同类型的拷贝对象.
 */
function newObj(obj)
{
	if(typeof(obj)=='object')
	{
		if(!obj)
			return null;
		var o;
		if(typeof(obj.length) == 'number')//有序
		{
			o = new Array();
			for(i in obj)
				o.push(newObj(obj[i]));
		}
		else//无序
		{
			o = new Object();
			for(i in obj)
				o[i] = newObj(obj[i]);
		}
		return o;
	}
	else
		return obj;
}
/*	计算笛卡尔积
 *	@param: 
 *		obj(Array[][]).
 *	@return: (Array[][]).
 */
function cartesian(arr1)
{
	if(typeof(arr1.length)!='number')
		return;
	var temp1 = new Array();
	for(var i in arr1)
	{
		if(i==0)
		{
			for(var j in arr1[i])
			{
				temp1[j] = new Array();
				temp1[j].push(arr1[0][j]);
			}
		}
		else
		{
			var temp2 = new Array();
			for(var j in temp1)
			{
				for(var k in arr1[i])
				{
					var temp3 = newObj(temp1[j]);
					temp3.push(arr1[i][k]);
					temp2.push(temp3);
				}
			}
			temp1 = temp2;
		}
	}
	return temp1;
}
/*	将数字转换成指定长度的字符串
 *	@param: 
 *		n(number) 数字, i(number) 字符串长度.
 *	@return: (string).
 */
function num2str(n,i)
{
	var t = new String();
	t = '' + n;
	if(t.length<i)
		for(var j=t.length;j<i;j++)
			t = '0' + t;
	else if(t.length>i)
		t = t.substring(t.length - i,t.length)
	return t;
}