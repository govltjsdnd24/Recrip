<script setup>
defineProps({ article: Object });

</script>

<template>
  <tr class="text-center">
    <th scope="row">{{ article.articleno }}</th>
    <td class="text-start">
      <router-link
        :to="{ name: 'BoardView', params: { articleno: article.articleno } }"
        class="article-title link-dark"
      >
        {{ article.subject }}
      </router-link>
    </td>
    <td>{{ article.userid }}</td>
    <td>{{ article.hit }}</td>
    <td>{{ article.registdate }}</td>
  </tr>
</template>

<style scoped>
a {
  text-decoration: none;
}
</style>
