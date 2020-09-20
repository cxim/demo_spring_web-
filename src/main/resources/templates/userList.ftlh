<#import 'parts/common.ftlh' as c>

<@c.page>
	<a href="/main">To main page</a>
	<br/>
	List Of Users
	<table>
		<thead>
		<tr>
			<th>Name</th>
			<th>Role</th>
			<th>Action</th>
		</tr>
		</thead>
		<tbody>
			<#list users as user>
				<tr>
					<td>${user.username}</td>
					<td><#list user.roles as role>${role}<#sep>, </#list></td>
					<td><a href="/user/${user.id}">Edit status</a></td>
				</tr>
			</#list>
		</tbody>
	</table>
</@c.page>