package {{ cookiecutter.basePackage }}.biz.social.follow;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import {{ cookiecutter.basePackage }}.biz.auth.profile.Profile;
import {{ cookiecutter.basePackage }}.biz.auth.profile.ProfileService;
import {{ cookiecutter.basePackage }}.common.controller.AuthBaseController;
import {{ cookiecutter.basePackage }}.common.request.PaginationRequest;
import {{ cookiecutter.basePackage }}.common.response.R;
import {{ cookiecutter.namespace }}.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 用户关注管理
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/social")
public class FollowController extends AuthBaseController {

    final FollowService followService;
    final ProfileService profileService;

    /**
     * 关注
     *
     * @param followId 被关注用户ID
     */
    @GetMapping("/follow")
    public R<Void> follow(@RequestParam Integer followId) {
        if (Objects.equals(followId, getUserId())) {
            return R.fail("不能关注自己");
        }

        followService.follow(getUserId(), followId);
        return R.ok();
    }

    /**
     * 取消关注
     *
     * @param followId 被关注用户ID
     */
    @GetMapping("/unfollow")
    public R<Void> unfollow(@RequestParam Integer followId) {
        if (Objects.equals(followId, getUserId())) {
            return R.fail("不能取消关注自己");
        }

        followService.unfollow(getUserId(), followId);
        return R.ok();
    }

    /**
     * 获取粉丝列表
     */
    @GetMapping("/followers")
    public R<Page<Profile>> getFollowers(@Valid PaginationRequest req) {
        Page<Profile> p = new Page<>(req.getPageNum(), req.getPageSize());
        Page<Integer> followers = followService.getFollowers(getUserId(), req);
        if (followers == null || followers.getTotal() == 0) {
            return R.ok(p);
        }

        p.setTotal(followers.getTotal());
        p.setRecords(getProfiles(followers.getRecords()));
        return R.ok(p);
    }

    /**
     * 获取关注列表
     */
    @GetMapping("/followings")
    public R<Page<Profile>> getFollowings(@Valid PaginationRequest req) {
        Page<Profile> p = new Page<>(req.getPageNum(), req.getPageSize());
        Page<Integer> followings = followService.getFollowing(getUserId(), req);
        if (followings == null || followings.getTotal() == 0) {
            return R.ok(p);
        }

        p.setTotal(followings.getTotal());
        p.setRecords(getProfiles(followings.getRecords()));
        return R.ok(p);
    }

    /**
     * 个人统计
     */
    @GetMapping("/follow-stat")
    public R<FollowStatResponse> stat() {
        Long countFollowing = followService.countFollowing(getUserId());
        Long countFollowers = followService.countFollowers(getUserId());
        FollowStatResponse response = new FollowStatResponse(countFollowing, countFollowers);
        return R.ok(response);
    }

    /**
     * 获取昵称和头像
     *
     * @param userIds 用户ID列表
     */
    private List<Profile> getProfiles(List<Integer> userIds) {
        if (userIds == null || userIds.isEmpty()) {
            return Collections.emptyList();
        }

        LambdaQueryWrapper<Profile> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Profile::getUserId, Profile::getNickname, Profile::getAvatar);
        wrapper.in(Profile::getUserId, userIds);
        return profileService.list(wrapper);
    }

}
