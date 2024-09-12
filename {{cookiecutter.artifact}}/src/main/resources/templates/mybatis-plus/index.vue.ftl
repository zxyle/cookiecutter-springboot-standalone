<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">

      <#list table.columns as column>
        <el-form-item label="${column.comment}" prop="${column.property}">
          <el-input
                  v-model="queryParams.${column.property}"
                  placeholder="请输入${column.comment}"
                  clearable
                  style="width: 200px"
                  @keyup.enter="handleQuery"
          />
        </el-form-item>
      </#list>

      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['${table.biz}:${table.name}:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['${table.biz}:${table.name}:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['${table.biz}:${table.name}:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['${table.biz}:${table.name}:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="List" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" width="100" />
      <#list table.columns as column>
        <el-table-column label="${column.comment}" align="center" prop="${column.property}" />
      </#list>

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" >修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改对话框 -->
    <el-dialog :title="title" v-model="open" width="780px" append-to-body>
      <el-form ref="noticeRef" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <#list table.columns as column>
            <el-col :span="24">
              <el-form-item label="${column.comment}" prop="${column.property}">
              <#if column.javaType == "LocalDate">
                <el-date-picker v-model="form.${column.property}" type="date" placeholder="请输入${column.comment}" :size="size" />
              <#elseif column.javaType == "LocalDateTime">
                <el-date-picker v-model="form.${column.property}" type="datetime" placeholder="请输入${column.comment}" :size="size" />
              <#elseif column.javaType == "Integer">
                <el-input-number v-model="form.${column.property}" placeholder="请输入${column.comment}" />
              <#elseif column.javaType == "Boolean">
                <el-switch v-model="form.${column.property}" />
              <#else>
                <el-input v-model="form.${column.property}" placeholder="请输入${column.comment}" />
              </#if>
              </el-form-item>
            </el-col>
          </#list>
        </el-row>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

  </div>
</template>

<script setup name="Person">

  import { page, get, del, add, update, batchDelete } from "@/api/tool/person";

  const { proxy } = getCurrentInstance();

  const List = ref([]);
  const open = ref(false);
  const loading = ref(true);
  const showSearch = ref(true);
  const ids = ref([]);
  const single = ref(true);
  const multiple = ref(true);
  const total = ref(0);
  const title = ref("");

  const data = reactive({
    form: {},
    queryParams: {
      pageNum: 1,
      pageSize: 10
    },
    rules: {
      <#list table.columns as column>
      <#if !column.nullable>
      ${column.property}: [{ required: true, message: "${column.comment}不能为空", trigger: "blur" }],
      </#if>
      </#list>
    },
  });

  const { queryParams, form, rules } = toRefs(data);

  /** 查询列表 */
  function getList() {
    loading.value = true;
    page(queryParams.value).then(response => {
      List.value = response.data.records;
      total.value = response.data.total;
      loading.value = false;
    });
  }

  /** 取消按钮 */
  function cancel() {
    open.value = false;
    reset();
  }

  /** 表单重置 */
  function reset() {
    form.value = {
    <#list table.columns as column>
      ${column.property}: undefined,
    </#list>
    };
    proxy.resetForm("noticeRef");
  }

  /** 搜索按钮操作 */
  function handleQuery() {
    queryParams.value.pageNum = 1;
    getList();
  }

  /** 重置按钮操作 */
  function resetQuery() {
    proxy.resetForm("queryRef");
    handleQuery();
  }

  /** 多选框选中数据 */
  function handleSelectionChange(selection) {
    ids.value = selection.map(item => item.id);
    single.value = selection.length != 1;
    multiple.value = !selection.length;
  }

  /** 新增按钮操作 */
  function handleAdd() {
    reset();
    open.value = true;
    title.value = "新增${table.comment}";
  }

  /**修改按钮操作 */
  function handleUpdate(row) {
    reset();
    const dataId = row.id || ids.value;
    get(dataId).then(response => {
      form.value = response.data;
      open.value = true;
      title.value = "修改${table.comment}";
    });
  }

  /** 提交按钮 */
  function submitForm() {
    proxy.$refs["noticeRef"].validate(valid => {
      if (valid) {
        if (form.value.id != undefined) {
          update(form.value).then(response => {
            proxy.$modal.msgSuccess("修改成功");
            open.value = false;
            getList();
          });
        } else {
          add(form.value).then(response => {
            proxy.$modal.msgSuccess("新增成功");
            open.value = false;
            getList();
          });
        }
      }
    });
  }

  /** 删除按钮操作 */
  function handleDelete(row) {
    const aa = row.id || ids.value
    proxy.$modal.confirm('是否确认删除编号为"' + aa + '"的数据项？').then(function() {
      if (row.id) {
        return del(row.id)
      }
      if (ids.value){
        return batchDelete({ids: ids.value.join(',')})
      }
    }).then(() => {
      getList();
      proxy.$modal.msgSuccess("删除成功");
    }).catch(() => {});
  }

  function handleExport(){

  }

  getList()

</script>
