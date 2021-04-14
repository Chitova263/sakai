import { html } from 'lit-html';
import { unsafeHTML } from 'lit-html/directives/unsafe-html';
import fetchMock from "fetch-mock";
import { styles } from "./styles/sakai-styles.js";
import { conversationsI18n } from "./i18n/conversations-i18n.js";
import { topic1Data } from "./data/conversations/topic1-data.js";

import '../js/conversations/sakai-topic.js';

export default {
  title: 'Sakai Topic',
  decorators: [storyFn => {

    parent.portal = {locale: "en-GB", userId: "mike"};

    const post = {
      id: "post5",
      creator: "adrian",
      created: Date.now(),
      creatorDisplayName: "Adrian Fish",
      replyable: true,
    };

    fetchMock
      .get(/sakai-ws\/rest\/i18n\/getI18nProperties.*/, conversationsI18n, {overwriteRoutes: true})
      .get(/api\/conversations\/topics\/topic1/, topic1Data, {overwriteRoutes: true})
      .post(/api\/conversations\/posts/, (url, opts) => {

        const requestPost = JSON.parse(opts.body);
        post.id = ""+Math.floor(Math.random() * 20) + 1;
        post.message = requestPost.message;
        post.parentPost = requestPost.parentPost;
        post.parentTopic = requestPost.parentTopic;
        return post;
      }, {overwriteRoutes: true})
      .get("*", 500, {overwriteRoutes: true});
    return storyFn();
  }],
};

export const BasicDisplay = () => {

  return html`
    ${unsafeHTML(styles)}
    <div>
      <sakai-topic topic-id="topic1"></sakai-topic>
    </div>
  `;
};
