import request from '@/utils/request'

// 分页查询${table.comment!}
export function page(query) {
  return request({
    url: '/${table.biz}${table.endpoint}',
    method: 'get',
    params: query
  })
}

// 按ID查询${table.comment!}
export function get(id) {
  return request({
    url: '/${table.biz}${table.endpoint}/' + id,
    method: 'get'
  })
}

// 新增${table.comment!}
export function add(data) {
  return request({
    url: '/${table.biz}${table.endpoint}',
    method: 'post',
    data: data
  })
}

// 按ID更新${table.comment!}
export function update(data) {
  return request({
    url: '/${table.biz}${table.endpoint}/' + data.id,
    method: 'put',
    data: data
  })
}

// 按ID删除${table.comment!}
export function del(id) {
  return request({
    url: '/${table.biz}${table.endpoint}/' + id,
    method: 'delete'
  })
}

// 批量删除${table.comment!}
export function batchDelete(query) {
  return request({
    url: '/${table.biz}${table.endpoint}/batch',
    method: 'delete',
    params: query
  })
}

// 导出${table.comment!}
export function exportExcel(query) {
  return request({
    url: '/${table.biz}${table.endpoint}',
    method: 'get',
    params: { ...query, export: true}
  })
}
